package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toast;

import com.example.contadordecalorias.databinding.ActivityMainBinding;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavigation();
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
}