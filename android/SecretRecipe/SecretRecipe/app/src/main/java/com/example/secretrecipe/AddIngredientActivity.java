package com.example.secretrecipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddIngredientActivity extends AppCompatActivity {
    private EditText ingredientName, ingredientQuantity, ingredientUnit;
    private Button addIngredientButton;
    int cnt=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        ingredientName = findViewById(R.id.ingredientName);
        ingredientQuantity = findViewById(R.id.ingredientQuantity);
        ingredientUnit = findViewById(R.id.ingredientUnit);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ingredientName.getText().toString();
                String quantity = ingredientQuantity.getText().toString();
                String unit = ingredientUnit.getText().toString();
                String userId = "userId"; // Replace with authenticated user ID
                String ingredientId = "ingredientId"+(++cnt);
                addIngredient(userId, ingredientId, name, quantity, unit);


                //ingredientAPI.addIngredient(userId, ingredientId, name, quantity, unit);
            }
        });
    }
    public void addIngredient(String userId, String ingredientId, String name, String quantity, String unit) {
        DatabaseReference ingredientRef = FirebaseDatabase.getInstance()
                .getReference("ingredients")
                .child("ingredients")
                .child(userId)
                .child(ingredientId);

        ingredientRef.child("name").setValue(name);
        ingredientRef.child("quantity").setValue(quantity);
        ingredientRef.child("unit").setValue(unit);
    }

}
