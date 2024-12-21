package com.example.secretrecipe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateIngredientActivity extends AppCompatActivity {
    private EditText ingredientIdInput, ingredientQuantityInput;
    private Button updateIngredientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ingredient);

        ingredientIdInput = findViewById(R.id.ingredientIdInput);
        ingredientQuantityInput = findViewById(R.id.ingredientQuantityInput);
        updateIngredientButton = findViewById(R.id.updateIngredientButton);

        updateIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientId = ingredientIdInput.getText().toString();
                String newQuantity = ingredientQuantityInput.getText().toString();
                String userId = "userId";

                updateIngredient(ingredientId,newQuantity,userId);
            }
        });
    }
    public void updateIngredient(String ingredientId, String newQuantity, String userId) {
        DatabaseReference ingredientRef = FirebaseDatabase.getInstance()
                .getReference("ingredients")
                .child("ingredients")
                .child(userId)
                .child(ingredientId);
        ingredientRef.child("quantity").setValue(newQuantity);
    }
}
