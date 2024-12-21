package com.example.secretrecipe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RecipeManagerActivity extends AppCompatActivity {

    private static final int PICK_TXT_FILE_REQUEST = 1;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_manager);

        Button btnSelectFile = findViewById(R.id.btnProcessFile);
        tvStatus = findViewById(R.id.tvStatus);

        btnSelectFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain"); // Only show text files
            startActivityForResult(intent, PICK_TXT_FILE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_TXT_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                processFile(fileUri);
            }
        }
    }

    private void processFile(Uri fileUri) {
        String fileContent = readTextFile(fileUri); // Read the file
        if (fileContent.isEmpty()) {
            tvStatus.setText("Failed to read the file.");
            return;
        }

        List<Recipe> recipes = parseRecipes(fileContent);
        if (recipes.isEmpty()) {
            tvStatus.setText("No recipes found in the file.");
            return;
        }

        saveStructuredRecipesToFirebase(recipes);
    }

    private List<Recipe> parseRecipes(String fileContent) {
        List<Recipe> recipes = new ArrayList<>();
        String[] blocks = fileContent.split("\n\n");

        for (String block : blocks) {
            String[] lines = block.split("\n");
            String title = "";
            String ingredients = "";
            String instructions = "";

            for (String line : lines) {
                if (line.startsWith("Title:")) {
                    title = line.replace("Title:", "").trim();
                } else if (line.startsWith("Ingredients:")) {
                    ingredients = line.replace("Ingredients:", "").trim();
                } else if (line.startsWith("Instructions:")) {
                    instructions = line.replace("Instructions:", "").trim();
                }
            }

            if (!title.isEmpty() || !ingredients.isEmpty() || !instructions.isEmpty()) {
                recipes.add(new Recipe(title, ingredients, instructions));
            }
        }
        return recipes;
    }
    private String readTextFile(Uri fileUri) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getContentResolver().openInputStream(fileUri)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.e("FileReadError", "Error reading file: " + e.getMessage());
        }
        return content.toString();
    }
    private void saveStructuredRecipesToFirebase(List<Recipe> recipes) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference recipesRef = database.getReference("recipes");

        for (Recipe recipe : recipes) {
            recipesRef.push().setValue(recipe)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firebase", "Recipe saved: " + recipe.getTitle());
                        tvStatus.setText("Recipes saved successfully!");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firebase", "Error saving recipe: " + e.getMessage());
                        tvStatus.setText("Failed to save recipes to Firebase.");
                    });
        }
    }

}

