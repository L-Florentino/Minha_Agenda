package com.example.minha_agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NomeEt, EmailEt, SenhaEt, ConfirmarSenhaEt;
    Button RegistrarUsuario;
    TextView TenhoumacontaTXT;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    //
    String nome = " ", email = " ", password = "", confirmarpassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        NomeEt = findViewById(R.id.NomeEt);
        EmailEt = findViewById(R.id.EmailEt);
        SenhaEt = findViewById(R.id.SenhaEt);
        ConfirmarSenhaEt = findViewById(R.id.ConfirmarSenhaEt);
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario);
        TenhoumacontaTXT = findViewById(R.id.TenhoumacontaTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Aguarde por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                ValidarDados();
            }
        });

        TenhoumacontaTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void ValidarDados(){
        nome = NomeEt.getText().toString();
        email = EmailEt.getText().toString();
        password = SenhaEt.getText().toString();
        confirmarpassword = ConfirmarSenhaEt.getText().toString();

        if (TextUtils.isEmpty(nome)){
            Toast.makeText(this, "Insira seu nome", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Insira seu e-mail", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Insira sua senha", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmarpassword)) {
            Toast.makeText(this, "Confirme sua senha", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmarpassword)){
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
        }
        else {
            CriarConta();
        }
    }

    private void CriarConta() {
        progressDialog.setMessage("Criando sua conta...");
        progressDialog.show();

        //Criando um usuário no Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //
                        SalvarInformacao();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void SalvarInformacao() {
        progressDialog.setMessage("Salvando suas informações");
        progressDialog.dismiss();

        //Obtendo o id do usuário atual
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Dados = new HashMap<>();
        Dados.put("uid", uid);
        Dados.put("email", email);
        Dados.put("nomes", nome);
        Dados.put("password", password);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Dados)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, MenuPrincipal.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}