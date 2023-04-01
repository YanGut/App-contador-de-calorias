package com.example.contadordecalorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_name, edit_email, edit_password;
    private Button btn_register;
    String[] messages = {"Fill in all fields", "Registration completed successfully"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        getSupportActionBar().hide();
        startComponents();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();

                //Condicional para exibir uma menssagem caso o usuário tente se cadastrar sem preencher todos os campos
                if(name.isEmpty() == true || email.isEmpty() == true || password.isEmpty() == true) {

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {

                }

            }
        });
    }

    //método para conectar os objetos a uma referencia
    public void startComponents() {
        edit_name = findViewById(R.id.name_register);
        edit_email = findViewById(R.id.email_define);
        edit_password = findViewById(R.id.password_define);
        btn_register = findViewById(R.id.register_button);
    }

    public void registerUser(View v) {

        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful() == true) {
                    Snackbar snackbar = Snackbar.make(v, messages[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }
}