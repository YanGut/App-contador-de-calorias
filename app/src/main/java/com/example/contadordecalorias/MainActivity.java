package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import entities.Food;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    TextView caloriasActivity;
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


        //Database-------------------------------------
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.close();

        Toast.makeText(this, "Database funciona, e a comida foi criada!!!", Toast.LENGTH_SHORT).show();

        //sumAllCalories();
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

    public void sumAllCalories(){
        int totalCalories = 0;
        String id = user.getUid();
        for(int i = 0; i < 4; i++){
            String data = Integer.toString(i);
            db.collection("diary").document(user.getUid()).collection(data).get()
                    .addOnSuccessListener(documentSnapshot -> {

                        for(DocumentChange dc : documentSnapshot.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                foodArrayList.add(dc.getDocument().toObject(Food.class));
                            }
                        }
                    }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
        }

        for(Food f: foodArrayList){
            totalCalories += f.calories;
        }

        caloriasActivity.setText("Total de calorias: ");

    }

}