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

public class DeleteRecipe extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private ChildEventListener childEventListener;

    private RecipeNameAdapter recipeNameAdapter;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Recipe> searchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        recipeList = new ArrayList<>();
        searchList = new ArrayList<>();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


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
            ListView results = findViewById(R.id.listViewSimpleRemove);
            results.setAdapter(recipeNameAdapter);
            results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selected = (Recipe) parent.getItemAtPosition(position);
                    Intent intent = new Intent(DeleteRecipe.this, com.example.leungrw.virtualcookbook.ViewDetails.class);
                    Bundle recipeBundle = new Bundle();
                    recipeBundle.putString("recipeName",selected.getRecipeName());
                    recipeBundle.putInt("cookTime", selected.getCookTime());
                    recipeBundle.putInt("serves", selected.getServes());
                    recipeBundle.putStringArrayList("ingredients", selected.getIngredients());
                    recipeBundle.putStringArrayList("steps",selected.getSteps());
                    recipeBundle.putString("key",selected.getRid());
                    intent.putExtras(recipeBundle);
                    intent.putExtra("previous","delete");
                    startActivity(intent);
                    System.out.println(selected.toString());
                }
            });
            results.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selected = (Recipe) parent.getItemAtPosition(position);
                    myRef.child(selected.getRid()).removeValue();
                    recipeList.remove(selected);
                    for (Recipe r: recipeList)
                    {
                        System.out.println(r);
                    }
                    searchList.clear();
                    for (Recipe recipe: recipeList)
                    {
                        if (!recipe.getRecipeName().equalsIgnoreCase(selected.getRecipeName()))
                        {
                            searchList.add(recipe);
                        }
                    }
                    recipeNameAdapter.notifyDataSetChanged();
                    return true;
                }

            });



    }

    public void remove(View view)
    {
        InputMethodManager input= (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        recipeNameAdapter.clear();
        boolean found = false;
        searchList.clear();
        EditText searchName = (EditText) findViewById(R.id.editTextRemove);
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



    public void returnToMenu(View view)
    {
        Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.CookbookMainMenu.class);
        startActivity(intent);
    }

}
