package com.example.leungrw.virtualcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewDetailRecipes extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private ChildEventListener childEventListener;

    private RecipeNameAdapter recipeNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        ArrayList<Recipe> recipeList = new ArrayList<>();

        childEventListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                recipeNameAdapter.add( dataSnapshot.getValue(Recipe.class));
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
            recipeNameAdapter = new RecipeNameAdapter(this, recipeList);
            ListView results = findViewById(R.id.listViewRecipes);
            results.setAdapter(recipeNameAdapter);

            results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selected = (Recipe) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ViewDetailRecipes.this, com.example.leungrw.virtualcookbook.ViewDetails.class);
                    Bundle recipeBundle = new Bundle(); 
                    recipeBundle.putString("recipeName",selected.getRecipeName());
                    recipeBundle.putInt("cookTime", selected.getCookTime());
                    recipeBundle.putInt("serves", selected.getServes());
                    recipeBundle.putStringArrayList("ingredients", selected.getIngredients());
                    recipeBundle.putStringArrayList("steps",selected.getSteps());
                    recipeBundle.putString("key", selected.getRid());
                    intent.putExtras(recipeBundle);
                    intent.putExtra("previous", "view");
                    startActivity(intent);
                    System.out.println(selected.toString());
                }
            });





    }

    public void goMain (View view)
    {
        Intent intent = new Intent( this, com.example.leungrw.virtualcookbook.CookbookMainMenu.class);
        startActivity(intent);
    }
}
