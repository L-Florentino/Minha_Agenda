package com.example.minha_agenda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.minha_agenda.AdicionarNota.Adicionar_Nota;
import com.example.minha_agenda.ListarNotas.Listar_Notas;
import com.example.minha_agenda.NotasArquivadas.Notas_Arquivadas;
import com.example.minha_agenda.Perfil.Perfil_Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button AdicionarNotas, ListarNotas, Arquivados, Perfil, Sobre, EncerrarSessao;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView UidPrincipal, NomePrincipal, EmailPrincipal;
    ProgressBar progressBarDados;

    DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Inicializando os TextViews e o ProgressBar
        UidPrincipal = findViewById(R.id.UidPrincipal);
        NomePrincipal = findViewById(R.id.NomePrincipal);
        EmailPrincipal = findViewById(R.id.EmailPrincipal);
        progressBarDados = findViewById(R.id.progressBarDados);

        // Inicializando o banco de dados e autenticação
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Configurando a ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Minha Agenda");
        }

        // Inicializando os botões
        AdicionarNotas = findViewById(R.id.AdicionarNotas);
        ListarNotas = findViewById(R.id.ListarNotas);
        Arquivados = findViewById(R.id.Arquivados);
        Perfil = findViewById(R.id.Perfil);
        Sobre = findViewById(R.id.Sobre);  // Adicionando a inicialização do botão "Sobre"
        EncerrarSessao = findViewById(R.id.EncerrarSessao);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // Configurando listeners para cada botão
        AdicionarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Adicionar_Nota.class));
                Toast.makeText(MenuPrincipal.this, "Adicionar Nota", Toast.LENGTH_SHORT).show();
            }
        });

        ListarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Listar_Notas.class));
                Toast.makeText(MenuPrincipal.this, "Minhas Notas", Toast.LENGTH_SHORT).show();
            }
        });

        Arquivados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Notas_Arquivadas.class));
                Toast.makeText(MenuPrincipal.this, "Notas Arquivadas", Toast.LENGTH_SHORT).show();
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Perfil_Usuario.class));
                Toast.makeText(MenuPrincipal.this, "Perfil Usuario", Toast.LENGTH_SHORT).show();
            }
        });

        Sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuPrincipal.this, "Sobre", Toast.LENGTH_SHORT).show();
            }
        });

        EncerrarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SairApp();
            }
        });
    }

    @Override
    protected void onStart() {
        ComprovarInicioSessao();
        super.onStart();
    }

    private void ComprovarInicioSessao() {
        if (user != null) {
            // Usuário está logado
            CarregarDados();
        } else {
            // Usuário não está logado, dirigir para MainActivity
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            finish();
        }
    }

    private void CarregarDados() {
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Esconder o progress bar e mostrar os dados do usuário
                    progressBarDados.setVisibility(View.GONE);
                    UidPrincipal.setVisibility(View.VISIBLE);
                    NomePrincipal.setVisibility(View.VISIBLE);
                    EmailPrincipal.setVisibility(View.VISIBLE);

                    // Definindo nome e email do usuário
                    String uid = "" + snapshot.child("uid").getValue();
                    String nomes = "" + snapshot.child("nomes").getValue();
                    String email = "" + snapshot.child("email").getValue();

                    // Definindo o texto dos TextViews
                    UidPrincipal.setText(uid);
                    NomePrincipal.setText(nomes);
                    EmailPrincipal.setText(email);

                    // Habilitar botões
                    AdicionarNotas.setEnabled(true);
                    ListarNotas.setEnabled(true);
                    Arquivados.setEnabled(true);
                    Perfil.setEnabled(true);
                    Sobre.setEnabled(true);
                    EncerrarSessao.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Caso necessário, adicionar um tratamento de erro
            }
        });
    }

    private void SairApp() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Você saiu com sucesso", Toast.LENGTH_SHORT).show();
        finish();  // Finaliza a atividade atual
    }
}

