package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.contadordecalorias.adapterTools.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import entities.Food;

public class User_Diary extends AppCompatActivity {

    TextView title, calories;
    RecyclerView recyclerView;
    String data;
    ArrayList<Food> foodArrayList;
    MyAdapter myAdapter;
    String[] messages = {"Café da Manhã", "Almoço", "Jantar", "Lanche"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_diary);

        foodArrayList = new ArrayList<Food>();

        Bundle test = getIntent().getExtras();
        data = test.getString("title");
        int value = Integer.parseInt(data);

        title = findViewById(R.id.diaryTitle);
        title.setText(messages[value]);

        recyclerView = findViewById(R.id.recyclerViewDiary);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(getApplicationContext(),foodArrayList);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(myAdapter.context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {}

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Food food = foodArrayList.get(position);

                        db.collection("diary").document(user.getUid()).collection(data).document(food.getDocumentId()).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(getApplicationContext(), User_Diary.class);
                                intent.putExtra("title", data);
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                })
        );

    }

    @SuppressLint("NotifyDataSetChanged")
    public void EventChangeListener(){
        db.collection("diary").document(user.getUid()).collection(data).orderBy("name", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(documentSnapshot -> {

                    for(DocumentChange dc : documentSnapshot.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            foodArrayList.add(dc.getDocument().toObject(Food.class));
                        }
                    }

                    myAdapter.notifyDataSetChanged();

                    sumCalories();

                }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }

    public void backButton(View v){
        finish();
    }

    public void addFood(View v){
        Intent intent = new Intent(getApplicationContext(), Food_Select.class);
        intent.putExtra("type", data);
        finish();
        startActivity(intent);
    }

    public void sumCalories(){
        calories = findViewById(R.id.txt_totalCalories);

        int totalCalories = 0;

        for(Food f: foodArrayList){
            totalCalories += f.calories;
        }

        calories.setText("Total de calorias: " + totalCalories);
    }
}