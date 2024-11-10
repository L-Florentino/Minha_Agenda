package com.example.minha_agenda.AdicionarNota;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.minha_agenda.R;

public class Adicionar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Email_usuario, Data_hora_atual, Data, Estado;
    EditText Titulo, Descricao;
    Button Btn_Calendario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_nota);

        IniciarVariaveis();
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
}