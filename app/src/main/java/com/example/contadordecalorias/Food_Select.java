package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import entities.Food;

public class Food_Select extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Food> foodArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        foodArrayList = new ArrayList<Food>();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(Food_Select.this,foodArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();
    }

    public void EventChangeListener(){
        db.collection("foods").orderBy("name", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(documentSnapshot -> {

                    for(DocumentChange dc : documentSnapshot.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            foodArrayList.add(dc.getDocument().toObject(Food.class));
                        }
                    }

                    myAdapter.notifyDataSetChanged();

        }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }
}