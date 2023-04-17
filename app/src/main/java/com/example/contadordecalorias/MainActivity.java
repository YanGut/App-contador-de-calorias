package com.example.contadordecalorias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.contadordecalorias.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private Button btn_cafe_da_manha, btn_almoco, btn_jantar, btn_lanche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNavigation();
        iniciarComponentes();


        //Database-------------------------------------
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.close();

        Toast.makeText(this, "Database funciona, e a comida foi criada!!!", Toast.LENGTH_SHORT).show();
    }

    private void initNavigation(){
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        MainActivity.this.finish();
    }

    public void iniciarComponentes(){
        btn_cafe_da_manha = findViewById(R.id.cafe_da_manha_btn);
        btn_almoco = findViewById(R.id.almoco_btn);
        btn_jantar = findViewById(R.id.jantar_btn);
        btn_lanche = findViewById(R.id.lanche_btn);
    }

    public void telaCafeDaManha(View v){
        Intent intent = new Intent(MainActivity.this, Food_Select.class);
        startActivity(intent);
    }

}