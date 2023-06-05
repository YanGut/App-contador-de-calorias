package com.example.contadordecalorias.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.contadordecalorias.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import entities.Food;
import entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public TextView caloriasRestantes, caloriasTotais;
    ArrayList<Food> foodArrayList;
    int totalCal;
    int totalCalToShow;
    int totalProtein;
    int totalCarb;
    int totalFat;
    String CALORIAS_RESTANTES_KEY;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        if(savedInstanceState != null) {
//            totalCalToShow = savedInstanceState.getInt(CALORIAS_RESTANTES_KEY);
//        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        showAllUserParameters(user);

        foodArrayList = new ArrayList<Food>();

        EventChangeListener(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        caloriasRestantes = view.findViewById(R.id.Calorias_Restantes_Text);
        caloriasTotais = view.findViewById(R.id.Total_Calorias_Text);

        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState)
//    {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt(CALORIAS_RESTANTES_KEY, totalCalToShow);
//    }

    public void showAllUserParameters(FirebaseUser user) {
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                User userLog = documentSnapshot.toObject(User.class);

                int totalMetabolicRate = userLog.totalMetabolicRate;
                totalProtein = userLog.proteinConsumption;
                totalCarb = userLog.carbConsumption;
                totalFat = userLog.fatConsumption;

                totalCal = (totalMetabolicRate - ((int)(totalMetabolicRate * 0.2)));

                caloriasTotais.setText("Total de calorias: " + totalCal);
            } else {
                Log.d(TAG, "No such document");
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void EventChangeListener(FirebaseUser user){
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("diary").document(userId);

        for (int i = 0; i < 4; i++) {
            String parametro = Integer.toString(i);

            docRef.collection(parametro).get().addOnSuccessListener(documentSnapshot -> {

                for(DocumentChange dc : documentSnapshot.getDocumentChanges()){

                    if(dc.getType() == DocumentChange.Type.ADDED){
                        foodArrayList.add(dc.getDocument().toObject(Food.class));
                    }
                }

                sumCalories(foodArrayList);
                sumProtein(foodArrayList);
                sumCarb(foodArrayList);
                sumFat(foodArrayList);

            }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
        }


    }

    public void sumCalories(ArrayList<Food> foodArrayList){

        int totalCalories = 0;

        for(Food f: foodArrayList){
            totalCalories += f.calories;
        }

        totalCalToShow = (totalCal - totalCalories);

        caloriasRestantes.setText(String.valueOf(totalCalToShow));
    }

    public void sumProtein(ArrayList<Food> foodArrayList) {
        int totalProteins = 0;

        for (Food f: foodArrayList) {
            totalProteins += f.protein;
        }

        int totalProteinToShow = totalProtein - totalProteins;
    }

    public void sumCarb(ArrayList<Food> foodArrayList) {
        int totalCarbs = 0;

        for (Food f: foodArrayList) {
            totalCarbs += f.carb;
        }

        int totalProteinToShow = totalCarb - totalCarbs;
    }

    public void sumFat(ArrayList<Food> foodArrayList) {
        int totalFats = 0;

        for (Food f: foodArrayList) {
            totalFats += f.fat;
        }

        int totalProteinToShow = totalFat - totalFats;
    }

}