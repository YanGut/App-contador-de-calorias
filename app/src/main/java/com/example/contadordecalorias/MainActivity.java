package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database-------------------------------------
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.close();

        Toast.makeText(this, "Database funciona, e a comida foi criada!!!", Toast.LENGTH_SHORT).show();
    }
}