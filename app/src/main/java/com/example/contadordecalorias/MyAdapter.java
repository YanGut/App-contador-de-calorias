package com.example.contadordecalorias;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import entities.Food;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Food> foodArrayList;
    private AdapterView.OnItemClickListener mListener;

    public MyAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.food_card,parent,false);

        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Food food = foodArrayList.get(position);

        holder.foodName.setTag(food.getDocumentId());
        holder.foodName.setText(food.name);
        holder.foodCal.setText("Calorias: " + food.calories + " Kcal,");
        holder.foodProtein.setText("Proteínas: " + food.protein + " g,");
        holder.foodQuantity.setText("Quantidade: " + food.quantity + (food.unity ? " un" : " g"));

    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView foodName, foodCal, foodProtein, foodQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.txt_foodName);
            foodCal = itemView.findViewById(R.id.txt_foodCal);
            foodProtein = itemView.findViewById(R.id.txt_foodProtein);
            foodQuantity = itemView.findViewById(R.id.txt_foodQuantity);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(ArrayList<Food> filteredList){
        this.foodArrayList = filteredList;
        notifyDataSetChanged();
    }
}
