package com.example.minha_agenda.AdicionarNota;

import static com.example.minha_agenda.R.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minha_agenda.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Adicionar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Email_usuario, Data_hora_atual, Data, Estado;
    EditText Titulo, Descricao;
    Button Btn_Calendario;

    int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_nota);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        IniciarVariaveis();
        ObterDados();
        Obter_Data_Hora();

        Btn_Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();

                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                ano = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Adicionar_Nota.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int AnoSelecionado, int MesSelecionado, int DiaSelecionado) {

                        String diaFormatado, mesFormatado;

                        // Formatação do dia
                        if (DiaSelecionado < 10){
                            diaFormatado = "0"+ String.valueOf(DiaSelecionado);
                            // Antes: 7/11/2024 - Agora: 07/11/2024
                        }else {
                            diaFormatado = String.valueOf(DiaSelecionado);
                            // Exemplo: 13/08/2024
                        }

                        // Formatação do mês
                        int Mes = MesSelecionado + 1;

                        if (Mes < 10){
                            mesFormatado = "0"+ String.valueOf(Mes);
                            // Antes: 07/8/2024 - Agora: 07/08/2024
                        }else {
                            mesFormatado = String.valueOf(Mes);
                            // Exemplo: 11/10/2024 - 11/11/2024 - 11/12/2024
                        }

                        // Definir data no TextView
                        Data.setText(diaFormatado + "/" + mesFormatado + "/" + AnoSelecionado);
                    }
                }
                , ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    private void IniciarVariaveis() {
        Uid_Usuario = findViewById(R.id.Uid_Usuario);
        Email_usuario = findViewById(R.id.Email_usuario);
        Data_hora_atual = findViewById(R.id.Data_hora_atual);
        Data = findViewById(R.id.Data);
        Estado = findViewById(R.id.Estado);

        Titulo = findViewById(R.id.Titulo);
        Descricao = findViewById(R.id.Descricao);
        Btn_Calendario = findViewById(R.id.Btn_Calendario);
    }

    private void ObterDados() {
        String uid_recuperado = getIntent().getStringExtra("Uid");
        String email_recuperado = getIntent().getStringExtra("Email");

        Uid_Usuario.setText(uid_recuperado);
        Email_usuario.setText(email_recuperado);
    }

    private void Obter_Data_Hora() {
        String Data_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());
        Data_hora_atual.setText(Data_hora_registro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Adicionar_Nota_BD) {
            Toast.makeText(this, "Nota Adicionada", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
