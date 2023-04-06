package com.example.contadordecalorias;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.User;

public class UserConfig extends AppCompatActivity {

    public TextView txt_name, txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        txt_name = findViewById(R.id.text_user_name);
        txt_email = findViewById(R.id.text_user_email);

       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        alterarDados(user);

        getSupportActionBar().hide();
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
            } else {
                Log.d(TAG, "No such document");
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error getting document: " + e.getMessage()));
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();

        UserConfig.this.finish();
    }
}