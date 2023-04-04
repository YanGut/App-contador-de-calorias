package entities;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String name;
    public int age;
    public double height;
    public double weight;
    public double fat;
    public int activity;
    public String gender;
    public String email;
    public boolean adm;


    public User(String name, int age, double height, double weight, double fat, int activity, String gender, String email){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.fat = fat;
        this.activity = activity;
        this.gender = gender;
        this.email = email;
        this.adm = false;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        result.put("height", height);
        result.put("weight", weight);
        result.put("fat", fat);
        result.put("activity", activity);
        result.put("gender", gender);
        result.put("email", email);
        result.put("adm", adm);
        return result;
    }

}
