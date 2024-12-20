package com.example.minha_agenda.ListarNotas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minha_agenda.Objetos.Nota;
import com.example.minha_agenda.ViewHolder.ViewHolder_Nota;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.example.minha_agenda.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Listar_Notas extends AppCompatActivity {

    RecyclerView recyclerviewNotas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BANCO_DE_DADOS;

    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Nota, ViewHolder_Nota> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Nota> options;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_notas);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Minhas notas");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        recyclerviewNotas = findViewById(R.id.recyclerviewNotas);
        recyclerviewNotas.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BANCO_DE_DADOS = firebaseDatabase.getReference("Notas_Publicadas");
        dialog = new Dialog(Listar_Notas.this);
        listarNotasUsuarios();
    }

    private void listarNotasUsuarios() {
        options = new FirebaseRecyclerOptions.Builder<Nota>().setQuery(BANCO_DE_DADOS, Nota.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder_Nota viewHolder_nota, int position, @NonNull Nota nota) {
                viewHolder_nota.ColetarDados(
                        getApplicationContext(),
                        nota.getId_nota(),
                        nota.getUid_usuario(),
                        nota.getEmail_usuario(),
                        nota.getData_hora_atual(),
                        nota.getTitulo(),
                        nota.getDescricao(),
                        nota.getData_nota(),
                        nota.getEstado()
                );
            }

            @Override
            public ViewHolder_Nota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
                ViewHolder_Nota viewHolder_nota = new ViewHolder_Nota(view);
                viewHolder_nota.setOnClickListener(new ViewHolder_Nota.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(Listar_Notas.this, "on item click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        String id_nota = getItem(position).getId_nota();

                        // Declarando as vistas
                        Button CD_Apagar, CD_Atualizar;

                        // Realizar a conexão com as vistas
                        dialog.setContentView(R.layout.dialogo_opcoes);
                        
                        // Declarando as vistas
                        CD_Apagar = dialog.findViewById(R.id.CD_Apagar);
                        CD_Atualizar = dialog.findViewById(R.id.CD_Atualizar);
                        
                        CD_Apagar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ApagarNota(id_nota);
                                dialog.dismiss();
                            }
                        });
                        
                        CD_Atualizar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(Listar_Notas.this, "Nota atualizada", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                return viewHolder_nota;
            }
        };

        linearLayoutManager = new LinearLayoutManager(Listar_Notas.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerviewNotas.setLayoutManager(linearLayoutManager);
        recyclerviewNotas.setAdapter(firebaseRecyclerAdapter);
    }

    private void ApagarNota(String id_nota) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Listar_Notas.this);
        builder.setTitle("Apagar nota");
        builder.setMessage("Deseja realmente apagar esta nota?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Apagar nota no BD
                Query query = BANCO_DE_DADOS.orderByChild("id_nota").equalTo(id_nota);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Listar_Notas.this, "Nota apagada", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(Listar_Notas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Listar_Notas.this, "Cancelado pelo usuário", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter !=null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}