package com.example.leungrw.virtualcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewDetails extends AppCompatActivity {
    private Bundle recipeBundle;
    private Recipe recipe;
    private String previous;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

 	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        Intent intent = getIntent();
        recipeBundle = intent.getExtras();
        previous = intent.getStringExtra("previous");

        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete.setVisibility(View.GONE);
        buttonUpdate.setVisibility(View.GONE);
        TextView textUpdate = findViewById(R.id.buttonUpdate);
        textUpdate.setVisibility(View.GONE);
        if (previous.equals("delete"))
        {
            buttonDelete.setVisibility(View.VISIBLE);
        }


        recipe = new Recipe(recipeBundle.getString("recipeName"), recipeBundle.getInt("cookTime"), recipeBundle.getInt("serves"), recipeBundle.getStringArrayList("ingredients"), recipeBundle.getStringArrayList("steps"),recipeBundle.getString("key"));
        System.out.println(recipe);
        System.out.println(recipe.getIngredientsString());
        TextView textView_recipeName = findViewById(R.id.textView_detailRecipeName);
        TextView textView_cook = findViewById(R.id.textView_detailCookTime);
        TextView textView_serves = findViewById(R.id.textView_detailServes);
        TextView textView_ingredients = findViewById(R.id.textView_detailIngredients);
        TextView textView_steps = findViewById(R.id.textView_detailSteps);
        textView_recipeName.setText(recipe.getRecipeName());
        textView_cook.setText("Cook Time: " + recipe.getCookTime()+" minutes");
        textView_serves.setText("Serves: " + recipe.getServes());
        textView_ingredients.setText("Ingredients:\n" + recipe.getIngredientsString());
        textView_steps.setText("Steps:\n" + recipe.getStepsString());


         if (previous.equals("update"))
         {
             buttonUpdate.setVisibility(View.VISIBLE);
         }





    }



    public void updateClicked (View view)
    {
        Intent intentUpdate = new Intent(this, com.example.leungrw.virtualcookbook.AddRecipe.class);
        intentUpdate.putExtras(recipeBundle);
        startActivity(intentUpdate);
    }

    public void deleteClicked(View view)
    {
        myRef.child(recipe.getRid()).removeValue();
        Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.DeleteRecipe.class);
        startActivity(intent);

    }

    public void goBacktoRecipeList(View view)
    {
        Intent intent;
        if (previous.equals("view"))
        {
            intent =  new Intent(this, com.example.leungrw.virtualcookbook.ViewDetailRecipes.class);
        }
        else if (previous.equals("search"))
        {
            intent = new Intent(this, com.example.leungrw.virtualcookbook.SearchRecipe.class);
        }
        else if (previous.equals("update"))
        {
            intent = new Intent (this, com.example.leungrw.virtualcookbook.UpdateRecipe.class);
        }
        else
        {
            intent = new Intent(this, com.example.leungrw.virtualcookbook.DeleteRecipe.class);
        }
        startActivity(intent);
    }





}
