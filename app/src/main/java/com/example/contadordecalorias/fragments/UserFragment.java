package com.example.contadordecalorias.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contadordecalorias.R;
import com.example.contadordecalorias.UserConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    public TextView txt_name, txt_email, txt_age, txt_height, txt_weight, txt_gender;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        alterarDados(user);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        txt_name = view.findViewById(R.id.text_user_name);
        String currentValueName = txt_name.getText().toString();

        txt_email = view.findViewById(R.id.text_user_email);
        String currentValueEmail = txt_email.getText().toString();

        txt_gender = view.findViewById(R.id.text_user_gender);

        txt_age = view.findViewById(R.id.text_user_age);

        txt_height = view.findViewById(R.id.text_user_height);

        txt_weight = view.findViewById(R.id.text_user_weight);


        return view;
    }
    private void alterarDados(FirebaseUser user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                User userLog = documentSnapshot.toObject(User.class);
                txt_name.setText(userLog.name);
                txt_email.setText(user.getEmail());
                txt_gender.setText(String.valueOf(userLog.gender));
                txt_age.setText(String.valueOf(userLog.age));
                txt_height.setText(String.valueOf(userLog.height));
                txt_weight.setText(String.valueOf(userLog.weight));
            } else {
                Log.d(TAG, "No such document");
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }


}