package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import entities.Food;

public class Food_Select extends AppCompatActivity {

    SearchView edt_textSearch;
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

    private void filterList(String text){
        ArrayList<Food> filteredList = new ArrayList<>();
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