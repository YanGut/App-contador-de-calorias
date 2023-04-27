package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.Food;

public class Food_Create extends AppCompatActivity {

    private EditText name, protein, carb, calories, fiber, fat, quantity;
    String[] messages = {"Preencha todos os campos, doidim!", "Comida criada oia", "User not found"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_create);

        setVariables();
    }

    public void setVariables(){
        name = findViewById(R.id.name_register);
        protein = findViewById(R.id.protein);
        carb = findViewById(R.id.carb);
        calories = findViewById(R.id.calories);
        fiber = findViewById(R.id.fiber);
        quantity = findViewById(R.id.quantity);
        fat = findViewById(R.id.fat);
    }

    public void foodRegister(View v){
        String foodName = name.getText().toString();
        Integer foodProtein = Integer.parseInt(protein.getText().toString());
        Integer foodCarb = Integer.parseInt(carb.getText().toString());
        Integer foodCalories = Integer.parseInt(calories.getText().toString());
        Integer foodFiber = Integer.parseInt(fiber.getText().toString());
        Integer foodQuantity = Integer.parseInt(quantity.getText().toString());
        Integer foodFat = Integer.parseInt(fat.getText().toString());

        if(foodName.isEmpty() || foodProtein.equals(null) || foodFat.equals(null) || foodCarb.equals(null) || foodCalories.equals(null) || foodFiber.equals(null) || foodQuantity.equals(null)){
            Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }else{
            Food food = new Food(foodName,foodProtein,foodCalories,foodCarb,foodFat,foodFiber,foodQuantity,true);

            db.collection("Food").document().set(food.toMap());
        }
    }
}