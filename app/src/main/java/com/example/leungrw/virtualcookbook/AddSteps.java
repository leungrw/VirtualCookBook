package com.example.leungrw.virtualcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddSteps extends AppCompatActivity {


    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private Bundle recipeBundle;
    private ArrayList<String> steps;
    private int stepsCount;
    private ArrayAdapter<String> stepsAdapter;
    private String key;



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

    private void checkEmpty() {
        Button buttonSteps = findViewById(R.id.buttonAddStep);
        Button buttonRecipe = findViewById(R.id.buttonAddRecipe);
        EditText editStep = findViewById(R.id.editTextStep);
        String step = editStep.getText().toString();
        if(step.equals("") || step.replaceAll("\\s+","").length()==0)
        {
            buttonSteps.setEnabled(false);
        }
        else {
            buttonSteps.setEnabled(true);
        }
        if (step.length()!=0 && (step.replaceAll("\\s+","").length()==0))
        {
            Toast.makeText(this, "Invalid step", Toast.LENGTH_LONG).show();
        }
        if (stepsCount!=0)
        {
            buttonRecipe.setEnabled(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstep);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        Intent intent = getIntent();
        recipeBundle = intent.getExtras();
        steps = new ArrayList<String>();
        key = "";
        if (recipeBundle!=null)
        {

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            if (recipeBundle.getStringArrayList("steps")!=null)
            {
                steps = recipeBundle.getStringArrayList("steps");
            }
            if (recipeBundle.getString("key")!=null)
            {
                key = recipeBundle.getString("key");
            }
        }



        stepsCount = steps.size();
        stepsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, steps);

        EditText editTextStep = findViewById(R.id.editTextStep);
        editTextStep.addTextChangedListener(textWatcher);
        Button buttonRecipe = findViewById(R.id.buttonAddRecipe);
        checkEmpty();

        ListView listView = (ListView) findViewById(R.id.listViewSteps);
        listView.setAdapter(stepsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                steps.remove(selected);
                stepsCount = steps.size();

                if (stepsCount == 0)
                {
                    Button buttonRecipe = findViewById(R.id.buttonAddRecipe);
                    buttonRecipe.setEnabled(false);
                }
                stepsAdapter.notifyDataSetChanged();
            }
        });

    }


    public void addStep ( View view )
    {
        EditText editTextStep = findViewById(R.id.editTextStep);
        String step = editTextStep.getText().toString();
        if (step.length()!=0 && (step.replaceAll("\\s+","").length()!=0))
        {
                steps.add(step);
                stepsCount=steps.size();
        }

            editTextStep.setText("");
            stepsAdapter.notifyDataSetChanged();
            ListView listView = (ListView) findViewById(R.id.listViewSteps);
            listView.setAdapter(stepsAdapter);
    }


    public void addRecipe (View view)
    {
        EditText editTextStep = findViewById(R.id.editTextStep);
        String step = editTextStep.getText().toString();
        if ((step.length()!=0 && (step.replaceAll("\\s+","").length()!=0))|| stepsCount!=0)
        {
            addStep(view);

            String recipeName = recipeBundle.getString("recipeName");
            int cookTime = recipeBundle.getInt("cookTime");
            int serves = recipeBundle.getInt("serves");
            ArrayList<String> ingredients =  recipeBundle.getStringArrayList("ingredients");
            if (key.equals(""))
            {
                key = myRef.push().getKey();
            }
            Recipe recipe = new Recipe(recipeName, cookTime,serves,ingredients,steps,key);
            myRef.child(key).setValue(recipe);
            Toast.makeText(this, recipe.getRecipeName()+" was sucessfully added.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.CookbookMainMenu.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Invalid Step", Toast.LENGTH_LONG).show();
            editTextStep.setText("");
        }

    }

    public void goBackToRecipe (View view)
    {
        Intent intent = new Intent(this, com.example.leungrw.virtualcookbook.AddIngredients.class);
        intent.putExtras(recipeBundle);
        startActivity(intent);
    }
}
