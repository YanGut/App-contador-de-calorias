package com.example.contadordecalorias.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.contadordecalorias.FormCadastro;
import com.example.contadordecalorias.FormLogin;
import com.example.contadordecalorias.MainActivity;
import com.example.contadordecalorias.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.User;

public class Register03 extends AppCompatActivity {

    private Toolbar toolbar;
    private ToggleButton mToggleButton1, mToggleButton2, mToggleButton3, mToggleButton4;
    private ToggleButton mLastSelectedButton;
    private TextView tex_tela_login;
    private Button button;
    private int mSelectedValue = -1;

    private EditText edit_name, edit_email, edit_password, edit_gender, edit_activity, edit_age, edit_height, edit_weight, edit_fat;
    private Button btn_register;
    String[] messages = {"Fill in all fields", "Registration completed successfully", "Error to register user"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Para ter a toolbar
        setContentView(R.layout.activity_re);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Para poder pegar um valor diferente de cada botão e não deixar a pessoa escolher multiplas opções
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);

        button = findViewById(R.id.buttonNext);
        edit_name.addTextChangedListener(textWatcher);
        edit_email.addTextChangedListener(textWatcher);
        edit_password.addTextChangedListener(textWatcher);


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Não é necessário fazer nada antes que o texto seja alterado
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Quando o texto é alterado, verifique se todos os campos estão preenchidos
            button.setEnabled(allFieldsAreFilled());

        }

        @Override
        public void afterTextChanged(Editable s) {
            // Não é necessário fazer nada depois que o texto é alterado
        }
    };
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

                                Register03.this.finish();
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
    private boolean validateFields() {
        String username = edit_name.getText().toString().trim();
        String email = edit_email.getText().toString().trim();
        String password = edit_password.getText().toString().trim();

        if (TextUtils.isEmpty(username) || username.length() < 5) {
            edit_name.setError("Username must be at least 5 characters long.");
            return false;
        }

        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            edit_email.setError("Please enter a valid email address.");
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4) {
            edit_password.setError("Password must be at least 4 characters long.");
            return false;
        }

        return true;
    }


    private boolean allFieldsAreFilled() {
        String username = edit_name.getText().toString().trim();
        String email = edit_email.getText().toString().trim();
        String password = edit_password.getText().toString().trim();

        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);
    }




    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "Voltou", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register03.this, Register02.class);
        startActivity(intent);
        return true;
    }

    public void loginScreen(View v){
        Intent intent = new Intent(Register03.this, FormLogin.class);
        startActivity(intent);

    }

    public void Finalizar(View v) {
        validateFields();
        if (validateFields()) {
            Intent intent = new Intent(Register03.this, FormLogin.class);
            startActivity(intent);

        }


    }


}