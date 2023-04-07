package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormLogin extends AppCompatActivity {

    private TextView tex_tela_cadastro, edt_email, edt_password;
    private FirebaseAuth auth;
    String[] messages = {"Fill in all fields", "Login sucess", "User not found"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        IniciarComponentes();

        tex_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });
    }

    public void IniciarComponentes(){
        tex_tela_cadastro = findViewById(R.id.text_screen_register);
        edt_email = findViewById(R.id.edit_email);
        edt_password = findViewById(R.id.edit_password);

        auth = FirebaseAuth.getInstance();
    }

    public void loginUser(View v){
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();

        if(email.equals("") || password.equals("")){
            // Campos não preenchidos
            Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    Intent intent = new Intent(FormLogin.this, UserConfig.class);
                    startActivity(intent);
                }else{
                    // Usuário não localizado
                    Snackbar snackbar = Snackbar.make(v, messages[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            });
        }
    }
}