package com.example.minha_agenda.AdicionarNota;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minha_agenda.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Adicionar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Email_usuario, Data_hora_atual, Data, Estado;
    EditText Titulo, Descricao;
    Button Btn_Calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_nota);

        // Configurando a ActionBar com verificação de null
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Inicializando as variáveis e obtendo os dados do Intent
        IniciarVariaveis();
        ObterDados();
        Obter_Data_Hora();

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

    private void ObterDados(){
        String uid_recuperado = getIntent().getStringExtra("Uid");
        String email_recuperado = getIntent().getStringExtra("Email");

        Uid_Usuario.setText(uid_recuperado);
        Email_usuario.setText(email_recuperado);
    }

    private void Obter_Data_Hora(){
        String Data_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());
        Data_hora_atual.setText(Data_hora_registro);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
