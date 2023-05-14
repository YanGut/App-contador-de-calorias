package com.example.contadordecalorias.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.contadordecalorias.FormLogin;
import com.example.contadordecalorias.MainActivity;
import com.example.contadordecalorias.R;
import com.google.firebase.auth.FirebaseAuth;

public class Register02 extends AppCompatActivity {

    private Toolbar toolbar;
    private ToggleButton mToggleButton1, mToggleButton2, mToggleButton3, mToggleButton4;
    private ToggleButton mLastSelectedButton;
    private TextView tex_tela_login;
    private Button button;
    private int mSelectedValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Para ter a toolbar
        setContentView(R.layout.activity_register02);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Para poder pegar um valor diferente de cada botão e não deixar a pessoa escolher multiplas opções
        mToggleButton1 = findViewById(R.id.toggleMasculino);
        mToggleButton2 = findViewById(R.id.toggleFeminino);
        button = findViewById(R.id.buttonNext);

        mToggleButton1.setTag(1);
        mToggleButton2.setTag(2);


        mToggleButton1.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mToggleButton2.setOnCheckedChangeListener(mOnCheckedChangeListener);




    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mSelectedValue = (int) buttonView.getTag();

                //Desativa os outros botões
                if (buttonView != mToggleButton1) {
                    mToggleButton1.setChecked(false);
                    estadoBotao(true);
                }
                if (buttonView != mToggleButton2) {
                    mToggleButton2.setChecked(false);
                    estadoBotao(true);
                }
            } else {
                estadoBotao(false);
                //Se nenhum botão estiver marcado ele fica com o valor de -1
                mSelectedValue = -1;
            }
        }
    };
    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "Voltou", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register02.this, Register01.class);
        startActivity(intent);
        return true;
    }

    public void loginScreen(View v){
        Intent intent = new Intent(Register02.this, FormLogin.class);
        startActivity(intent);

    }

    public void Register03  (View v){
        Intent intent = new Intent(Register02.this, Register03.class);
        startActivity(intent);

    }

    public void estadoBotao(boolean variavel){

        if (variavel==true) {
            button.setEnabled(true);
        }else {
            button.setEnabled(false);


        }
    }



}