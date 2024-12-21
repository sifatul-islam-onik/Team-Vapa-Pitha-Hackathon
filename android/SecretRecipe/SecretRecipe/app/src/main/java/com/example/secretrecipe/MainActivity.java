package com.example.secretrecipe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button addIngredientButton, updateIngredientButton;
    private Button viewIngredientsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIngredientButton = findViewById(R.id.addIngredientButton);
        updateIngredientButton = findViewById(R.id.updateIngredientButton);

        viewIngredientsButton = findViewById(R.id.viewIngredientsButton);

        Button btnOpenRecipeManager = findViewById(R.id.btnOpenRecipeManager);
        btnOpenRecipeManager.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecipeManagerActivity.class);
            startActivity(intent);
        });
        viewIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewIngredientActivity.class);
                startActivity(intent);
            }
        });
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddIngredientActivity.class);
                startActivity(intent);
            }
        });

        updateIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateIngredientActivity.class);
                startActivity(intent);
            }
        });
    }
}
