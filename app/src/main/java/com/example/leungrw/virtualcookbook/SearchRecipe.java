package com.example.leungrw.virtualcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class SearchRecipe extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private ChildEventListener childEventListener;

    private RecipeNameAdapter recipeNameAdapter;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Recipe> searchList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        recipeList = new ArrayList<>();
        searchList = new ArrayList<>();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        childEventListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                recipeList.add(dataSnapshot.getValue(Recipe.class));
                recipeNameAdapter.add(dataSnapshot.getValue(Recipe.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            };

            myRef.addChildEventListener(childEventListener);
            recipeNameAdapter = new RecipeNameAdapter(this, searchList);
            ListView results = findViewById(R.id.listViewSimpleSearch);
            results.setAdapter(recipeNameAdapter);
            results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selected = (Recipe) parent.getItemAtPosition(position);
                    Intent intent = new Intent(SearchRecipe.this, com.example.leungrw.virtualcookbook.ViewDetails.class);
                    Bundle recipeBundle = new Bundle();
                    recipeBundle.putString("recipeName",selected.getRecipeName());
                    recipeBundle.putInt("cookTime", selected.getCookTime());
                    recipeBundle.putInt("serves", selected.getServes());
                    recipeBundle.putStringArrayList("ingredients", selected.getIngredients());
                    recipeBundle.putStringArrayList("steps",selected.getSteps());
                    recipeBundle.putString("key", selected.getRid());
                    intent.putExtras(recipeBundle);
                    intent.putExtra("previous","search");
                    startActivity(intent);
                    System.out.println(selected.toString());
                }
            });


    }

    public void Search(View view)
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        recipeNameAdapter.clear();
        boolean found = false;
        EditText searchName = (EditText) findViewById(R.id.editTextSearch);
        String searchString = searchName.getText().toString();
        System.out.println(searchString);
        for (Recipe recipe: recipeList)
        {
            if (recipe.getRecipeName().equalsIgnoreCase(searchString))
            {
                searchList.add(recipe);
                found= true;
            }
        }
        if (!found)
        {
            for (Recipe recipe: recipeList)
            {
                searchList.add(recipe);
            }
            Toast.makeText(this, "Recipe for " + searchString +" is not Found.", Toast.LENGTH_LONG).show();
        }
        recipeNameAdapter.notifyDataSetChanged();
        searchName.setText("");


    }


    public void goBackMenu ( View view )
    {
    	Intent intent = new Intent( this, com.example.leungrw.virtualcookbook.CookbookMainMenu.class);
        startActivity(intent);

    }

}
