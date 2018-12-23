package com.example.leungrw.virtualcookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CookbookMainMenu extends AppCompatActivity {

 	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }

    public void newRecipe(View view)
    {
    	Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.AddRecipe.class);
    	startActivity(intent);
    }

    public void viewCollection (View view)
    {
        Intent intent = new Intent (this, com.example.leungrw.virtualcookbook.ViewDetailRecipes.class);
        startActivity(intent);
    }

    public void searchCollection (View view)
    {
        Intent intent = new Intent (this, com.example.leungrw.virtualcookbook.SearchRecipe.class);
        startActivity(intent);
    }

    public void updateRecipe (View view)
    {
        Intent intent = new Intent (this, com.example.leungrw.virtualcookbook.UpdateRecipe.class);
        startActivity(intent);
    }

    public void removeRecipe (View view)
    {
        Intent intent = new Intent (this, com.example.leungrw.virtualcookbook.DeleteRecipe.class);
        startActivity(intent);
    }








}
