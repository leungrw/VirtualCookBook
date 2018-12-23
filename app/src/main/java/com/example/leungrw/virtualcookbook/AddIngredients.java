package com.example.leungrw.virtualcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddIngredients extends AppCompatActivity {
    private Bundle recipeBundle;
    private ArrayList<String> ingredients;
    private int ingredCount;
    private ArrayAdapter<String> itemsAdapter;
    private Recipe recipe;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkEmpty();
        }
    };

    private void checkEmpty()
    {
        Button ibutton = findViewById(R.id.buttonAddIngredient);
        Button sbutton = findViewById(R.id.buttonAddStep);
        EditText editIngredient = findViewById(R.id.editTextIngredient);
        String ingredientString= editIngredient.getText().toString();
        if (ingredientString.equals("")||ingredientString.replaceAll("\\s+","").length()==0)
        {
            ibutton.setEnabled(false);
        }
        else {
            ibutton.setEnabled(true);
        }
        if (ingredientString.length()!=0 && (ingredientString.replaceAll("\\s+","").length()==0))
        {
            Toast.makeText(this, "Invalid ingredient", Toast.LENGTH_LONG).show();
        }
        if (ingredCount!=0)
        {
            sbutton.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingredient);
        Intent intent = getIntent();
        recipeBundle = intent.getExtras();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        ingredients = new ArrayList<>();

        if (recipeBundle!=null)
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            if (recipeBundle.getStringArrayList("ingredients") != null)
            {
                ingredients = recipeBundle.getStringArrayList("ingredients");
            }
        }

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients);
        ingredCount = ingredients.size();
        EditText editTextIngredients = findViewById(R.id.editTextIngredient);
        editTextIngredients.addTextChangedListener(textWatcher);
        Button sbutton = findViewById(R.id.buttonAddStep);
        checkEmpty();


        ListView listView = findViewById(R.id.listViewIngredients);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                ingredients.remove(selected);
                ingredCount = ingredients.size();
                if (ingredCount  == 0) {
                    Button buttonSteps = findViewById(R.id.buttonAddStep);
                    buttonSteps.setEnabled(false);
                }
                itemsAdapter.notifyDataSetChanged();

            }
        });


    }

    public void addIngredient( View view )
    {
        EditText editTextIngredients = findViewById(R.id.editTextIngredient);
        String ingredient = editTextIngredients.getText().toString();
        if (ingredient.length()!=0 && ingredient.replaceAll("\\s+","").length()!=0) {
            ingredients.add(ingredient);
            ingredCount=ingredients.size();
        }

        editTextIngredients.setText("");
        itemsAdapter.notifyDataSetChanged();
        ListView listView = (ListView) findViewById(R.id.listViewIngredients);
        listView.setAdapter(itemsAdapter);
    }

     public void goToSteps(View view)
     {
         EditText editTextIngredients = findViewById(R.id.editTextIngredient);
         String ingredient = editTextIngredients.getText().toString();
         if ((ingredient.length()!=0 && (ingredient.replaceAll("\\s+","").length()!=0))|| ingredCount!=0)
         {
             addIngredient(view);
             recipeBundle.putStringArrayList("ingredients",ingredients);
             Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.AddSteps.class);
             intent.putExtras(recipeBundle);
             startActivity(intent);

         }
         else
         {
             Toast.makeText(this, "Invalid ingredient", Toast.LENGTH_LONG).show();
             editTextIngredients.setText("");
         }

     }

    public void goBack ( View view )
    {
        Intent intent = new Intent( this, com.example.leungrw.virtualcookbook.AddRecipe.class);
        intent.putExtras(recipeBundle);



        startActivity(intent);

    }


}
