package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.Food;

public class Food_Create extends AppCompatActivity {

    private EditText name, protein, carb, calories, fiber, fat, quantity;
    private CheckBox unit;
    String[] messages = {"Preencha todos os campos, doidim!", "Comida criada oia", "Deu alguma coisa errada."};
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
        unit = findViewById(R.id.checkUni);
    }

    public boolean checarNulo(){
        String foodName = name.getText().toString();
        String foodProtein = protein.getText().toString();
        String foodCarb = carb.getText().toString();
        String foodCalories = calories.getText().toString();
        String foodFiber = fiber.getText().toString();
        String foodQuantity = quantity.getText().toString();
        String foodFat = fat.getText().toString();

        return foodName.isEmpty() || foodProtein.isEmpty() || foodFat.isEmpty() || foodCarb.isEmpty() || foodCalories.isEmpty() || foodFiber.isEmpty() || foodQuantity.isEmpty();
    }

    public void foodRegister(View v){

        if(checarNulo()){
            // Campos vazios
            Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }else {
            String foodName = name.getText().toString();
            double foodProtein = Double.parseDouble(protein.getText().toString());
            double foodCarb = Double.parseDouble(carb.getText().toString());
            int foodCalories = Integer.parseInt(calories.getText().toString());
            double foodFiber = Double.parseDouble(fiber.getText().toString());
            int foodQuantity = Integer.parseInt(quantity.getText().toString());
            double foodFat = Double.parseDouble(fat.getText().toString());
            boolean foodUni = unit.isChecked();

            Food food = new Food(foodName, foodProtein, foodCalories, foodCarb, foodFat, foodFiber, foodQuantity, foodUni);

            db.collection("foods").document().set(food.toMap()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }else{
                    // Deu ruim
                    Snackbar snackbar = Snackbar.make(v, messages[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            });
        }

    }
}