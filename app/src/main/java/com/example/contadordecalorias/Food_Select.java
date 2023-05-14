package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contadordecalorias.adapterTools.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import entities.Food;

public class Food_Select extends AppCompatActivity {

    SearchView edt_textSearch;
    RecyclerView recyclerView;
    ArrayList<Food> foodArrayList;
    ArrayList<Food> filteredList;
    MyAdapter myAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);

        Bundle test = getIntent().getExtras();
        data = test.getString("type");

        foodArrayList = new ArrayList<Food>();

        edt_textSearch = findViewById(R.id.edt_search);
        edt_textSearch.clearFocus();
        edt_textSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(Food_Select.this,foodArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(myAdapter.context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Food food = foodArrayList.get(position);
                        final Food[] food = new Food[1];

                        String id = view.findViewById(R.id.txt_foodName).getTag().toString();

                        db.collection("foods").document(id).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    food[0] = task.getResult().toObject(Food.class);

                                    db.collection("diary").document(user.getUid()).collection(data).document(food[0].getDocumentId()).set(food[0].toMap()).addOnCompleteListener(tasks -> {
                                        if (tasks.isSuccessful()) {

                                            Intent intent = new Intent(getApplicationContext(), User_Diary.class);
                                            intent.putExtra("title", data);
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        });

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );

    }

    @SuppressLint("NotifyDataSetChanged")
    public void EventChangeListener(){
        db.collection("foods").orderBy("name", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(documentSnapshot -> {

                    for(DocumentChange dc : documentSnapshot.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED) {
                            foodArrayList.add(dc.getDocument().toObject(Food.class));
                        }
                    }

                    myAdapter.notifyDataSetChanged();

        }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }

    private void filterList(String text){
        filteredList = new ArrayList<>();
        for(Food food : foodArrayList){
            if(food.name.toLowerCase().contains(text.toLowerCase())){

                filteredList.add(food);
            }

            if(filteredList.isEmpty()){
//                Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
            }else{
                myAdapter.setFilteredList(filteredList);
            }
        }
    }

}