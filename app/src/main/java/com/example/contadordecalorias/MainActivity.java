package com.example.contadordecalorias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.contadordecalorias.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import entities.Food;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    public static TextView caloriasActivity;
    private Button btn_cafe_da_manha, btn_almoco, btn_jantar, btn_lanche;
    ArrayList<Food> foodArrayList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNavigation();
        iniciarComponentes();

        Toast.makeText(this, "Database funciona, e a comida foi criada!!!", Toast.LENGTH_SHORT).show();

        //sumAllCalories();

        //definirCaloriasTotais();
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
        foodArrayList = new ArrayList<Food>();
        btn_cafe_da_manha = findViewById(R.id.cafe_da_manha_btn);
        btn_almoco = findViewById(R.id.almoco_btn);
        btn_jantar = findViewById(R.id.jantar_btn);
        btn_lanche = findViewById(R.id.lanche_btn);
        caloriasActivity = findViewById(R.id.titulo_diario);
    }

    public void screenDiary(View v){

        String index = v.getTag().toString();

        Intent intent = new Intent(getApplicationContext(), User_Diary.class);
        intent.putExtra("title", index);
        startActivity(intent);
    }

}