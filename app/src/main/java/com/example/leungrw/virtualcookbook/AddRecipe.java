package com.example.leungrw.virtualcookbook;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class AddRecipe extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private Bundle recipeBundle;

    private TextWatcher  textWatcher =new TextWatcher() {
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
        Button button = findViewById(R.id.buttonAdd);
        EditText editRecipeName = findViewById(R.id.editTextRecipeName);
        EditText editCookTime = findViewById(R.id.editTextCookTime);
        EditText editServe = findViewById(R.id.editTextServe);
        String recipeName = editRecipeName.getText().toString();
        String cookTimeString = editCookTime.getText().toString();
        String serveString= editServe.getText().toString();
        if (recipeName.equals("")||cookTimeString.equals("")||serveString.equals("")||(recipeName.replaceAll("\\s+","").length()==0))
        {
            button.setEnabled(false);
        }
        else {
            button.setEnabled(true);

        }
        if (recipeName.length()!=0 && (recipeName.replaceAll("\\s+","").length()==0))
        {
            Toast.makeText(this, "Invalid recipe name", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);
        Intent intent = getIntent();
        recipeBundle = intent.getExtras();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Recipe");

        EditText editRecipeName = findViewById(R.id.editTextRecipeName);
        EditText editCookTime = findViewById(R.id.editTextCookTime);
        EditText editServe = findViewById(R.id.editTextServe);
        editRecipeName.requestFocus();

        if (recipeBundle!=null)
        {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            editRecipeName.setText(recipeBundle.getString("recipeName"));
            editCookTime.setText(Integer.toString(recipeBundle.getInt("cookTime")));
            editServe.setText(Integer.toString(recipeBundle.getInt("serves")));
        }



        editRecipeName.addTextChangedListener(textWatcher);
        editCookTime.addTextChangedListener(textWatcher);
        editServe.addTextChangedListener(textWatcher);
        checkEmpty();
    }

    public void addRecipe(View view)
    {
        EditText editRecipeName = findViewById(R.id.editTextRecipeName);
        EditText editCookTime = findViewById(R.id.editTextCookTime);
        String recipeName = editRecipeName.getText().toString();
        String cookTimeString = editCookTime.getText().toString();
        int cookTime=0;
        if (cookTimeString.length()!=0) {
            cookTime = Integer.parseInt(editCookTime.getText().toString());
        }
        EditText editServe = findViewById(R.id.editTextServe);
        String serveString= editServe.getText().toString();
        int serve=0;
        if (serveString.length()!=0) {
            serve = Integer.parseInt(editServe.getText().toString());
        }

        if (recipeBundle==null){
            recipeBundle = new Bundle();
            recipeBundle.putString("recipeName",recipeName);
            recipeBundle.putInt("cookTime", cookTime);
            recipeBundle.putInt("serves", serve);
        }

        Intent intent = new Intent(this, AddIngredients.class);
        intent.putExtras(recipeBundle);
        startActivity(intent);


    }

    public void goHome(View view)
    {
	    Intent intent = new Intent(this, CookbookMainMenu.class);;
	    startActivity( intent);
    }

}
