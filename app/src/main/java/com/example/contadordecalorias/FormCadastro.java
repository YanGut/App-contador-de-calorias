package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.User;

public class        FormCadastro extends AppCompatActivity {

    private EditText edit_name, edit_email, edit_password, edit_gender, edit_activity, edit_age, edit_height, edit_weight, edit_fat;
    private Button btn_register;
    String[] messages = {"Fill in all fields", "Registration completed successfully", "Error to register user"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        startComponents();
    }

    //método para conectar os objetos a uma referencia
    public void startComponents() {
        edit_name = findViewById(R.id.name_register);
        edit_email = findViewById(R.id.email_define);
        edit_password = findViewById(R.id.password_define);
        edit_age = findViewById(R.id.protein);
        edit_weight = findViewById(R.id.calories);
        edit_height = findViewById(R.id.carb);
        edit_fat = findViewById(R.id.fat);
        edit_gender = findViewById(R.id.quantity);
        edit_activity = findViewById(R.id.fiber);
        btn_register = findViewById(R.id.register_button);
    }

    public void registerUser(View v) {

        String name = edit_name.getText().toString();
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();
        String gender = edit_gender.getText().toString();
        int activity = Integer.parseInt(edit_activity.getText().toString());

        int age = Integer.parseInt(edit_age.getText().toString());

        int height = Integer.parseInt(edit_height.getText().toString());
        float weight = Float.parseFloat(edit_weight.getText().toString());
        float fat = Float.parseFloat(edit_fat.getText().toString());

        double basalMetabolicRate = User.calculateBasalMetabolicRate(weight, height, age, gender);
        int totalMetabolicRate = User.calculateTotalMetabolicRate(basalMetabolicRate, activity);

        //Condicional para exibir uma menssagem caso o usuário tente se cadastrar sem preencher todos os campos
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {

            Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();

        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Cria um documento com as informações adicionais do usuário

                    User user = new User(name, age,  height,  weight,  fat,  activity, gender, email, basalMetabolicRate, totalMetabolicRate);

                    String uid = task.getResult().getUser().getUid();
                    FirebaseFirestore.getInstance().collection("users").document(uid)
                            .set(user.toMap())
                            .addOnSuccessListener(aVoid -> {
                                Snackbar snackbar = Snackbar.make(v, messages[1], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();

                                FormCadastro.this.finish();
                            })
                            .addOnFailureListener(e -> {
                                Snackbar snackbar = Snackbar.make(v, messages[2], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                            });
                } else {
                    // Falha ao criar uma conta de usuário
                    Snackbar snackbar = Snackbar.make(v, messages[3], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            });
        }
    }
}