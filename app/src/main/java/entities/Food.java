package entities;

import com.google.firebase.firestore.DocumentId;

import java.util.HashMap;
import java.util.Map;

public class Food {

    @DocumentId
    private String documentId;
    public String name;
    public int protein;
    public int calories;
    public int carb;
    public int fat;
    public int fiber;
    public int quantity;
    public boolean unity;


    public Food(String name, int protein, int calories, int carb, int fat, int fiber, int quantity, boolean unity) {
        this.name = name;
        this.protein = protein;
        this.calories = calories;
        this.carb = carb;
        this.fat = fat;
        this.fiber = fiber;
        this.quantity = quantity;
        this.unity = unity;
    }

    public Food(){}

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("protein", protein);
        result.put("calories", calories);
        result.put("carb", carb);
        result.put("fat", fat);
        result.put("fiber", fiber);
        result.put("quantity", quantity);
        result.put("unity", unity);
        return result;
    }
}
