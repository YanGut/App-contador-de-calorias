package com.example.contadordecalorias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FoodCreate extends AppCompatActivity {

    private EditText name, protein, carb, calories, fiber, fat, quantity;

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
        fiber = findViewById(R.id.fat);
        quantity = findViewById(R.id.quantity);
    }

    public void foodRegister(View v){
        String foodName = name.getText().toString();
        int foodProtein = Integer.parseInt(protein.getText().toString());
        int foodCarb = Integer.parseInt(carb.getText().toString());
        int foodCalories = Integer.parseInt(calories.getText().toString());
        int foodFiber = Integer.parseInt(fiber.getText().toString());
        int foodQuantity = Integer.parseInt(quantity.getText().toString());


    }
}