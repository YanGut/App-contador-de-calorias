package com.example.contadordecalorias.fragments;

import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
>>>>>>> Stashed changes

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contadordecalorias.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

<<<<<<< Updated upstream
=======
    public TextView caloriasRestantes, caloriasTotais, proteinaConsumida, proteinaTotal, carboidratoConsumido, carboidratoTotal,
                    gorduraConsumida, gorduraTotal;
    public ProgressBar progressBarCalories, progressBarProtein, progressBarCarb, progressBarFat;
    ArrayList<Food> foodArrayList;
    int totalCal;
    int totalCalToShow;
    int totalProtein;
    int totalProteinToShow;
    int totalCarb;
    int totalCarbToShow;
    int totalFat;
    int totalFatToShow;

>>>>>>> Stashed changes
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
<<<<<<< Updated upstream
=======

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        showAllUserParameters(user);

        foodArrayList = new ArrayList<Food>();

        EventChangeListener(user);
>>>>>>> Stashed changes
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
<<<<<<< Updated upstream
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
=======
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        caloriasRestantes = view.findViewById(R.id.Calorias_Restantes_Text);
        caloriasTotais = view.findViewById(R.id.Total_Calorias_Text);
        progressBarCalories = view.findViewById(R.id.progressBar_Calories);
        progressBarProtein = view.findViewById(R.id.progressbar_Protein);
        progressBarCarb = view.findViewById(R.id.progressbar_Carb);
        progressBarFat = view.findViewById(R.id.progressbar_Fat);
        proteinaConsumida = view.findViewById(R.id.textView_Protein_Consumed);
        proteinaTotal = view.findViewById(R.id.textView_Protein_Total);
        carboidratoConsumido = view.findViewById(R.id.textView_Carb_Consumed);
        carboidratoTotal = view.findViewById(R.id.textView_Carb_Total);
        gorduraConsumida = view.findViewById(R.id.textView_Fat_Consumed);
        gorduraTotal = view.findViewById(R.id.textView_Fat_Total);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        showAllUserParameters(user);

        foodArrayList = new ArrayList<Food>();

        EventChangeListener(user);

        return view;
    }

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

                progressBarCalories.setMax(totalCal);
                progressBarProtein.setMax(totalProtein);
                progressBarCarb.setMax(totalCarb);
                progressBarFat.setMax(totalFat);

                caloriasTotais.setText("Total de calorias: " + totalCal);
                proteinaTotal.setText(String.valueOf(totalProtein));
                carboidratoTotal.setText(String.valueOf(totalCarb));
                gorduraTotal.setText(String.valueOf(totalFat));
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

        progressBarCalories.setProgress(totalCalories);

        caloriasRestantes.setText(String.valueOf(totalCalToShow));
    }

    public void sumProtein(ArrayList<Food> foodArrayList) {
        int totalProteins = 0;

        for (Food f: foodArrayList) {
            totalProteins += (int)(f.protein);
        }

        totalProteinToShow = totalProtein - totalProteins;

        progressBarProtein.setProgress(totalProteins);

        proteinaConsumida.setText(String.valueOf(totalProteins));
    }

    public void sumCarb(ArrayList<Food> foodArrayList) {
        int totalCarbs = 0;

        for (Food f: foodArrayList) {
            totalCarbs += (int)(f.carb);
        }

        totalCarbToShow = totalCarb - totalCarbs;

        progressBarCarb.setProgress(totalCarbs);

        carboidratoConsumido.setText(String.valueOf(totalCarbs));
    }

    public void sumFat(ArrayList<Food> foodArrayList) {
        int totalFats = 0;

        for (Food f: foodArrayList) {
            totalFats += (int)(f.fat);
        }

        totalFatToShow = totalFat - totalFats;

        progressBarFat.setProgress(totalFats);

        gorduraConsumida.setText(String.valueOf(totalFats));
    }

>>>>>>> Stashed changes
}