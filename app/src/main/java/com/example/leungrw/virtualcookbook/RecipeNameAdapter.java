package com.example.leungrw.virtualcookbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeNameAdapter extends ArrayAdapter<Recipe> {
	private Context rContext;
	private ArrayList<Recipe> recipeList;

	public RecipeNameAdapter(Context context, ArrayList<Recipe> list)
	{
		super(context, 0, list);
		rContext = context;
		recipeList = list;
	}

	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItem = convertView;

		if(listItem == null)
			listItem=LayoutInflater.from(rContext).inflate(R.layout.recipe_simple_view,parent,false);
		Recipe currentRecipe = recipeList.get(position);
		TextView recipeName = (TextView) listItem.findViewById(R.id.textView_recipeName);
		recipeName.setText(currentRecipe.getRecipeName());
		TextView cookTime = (TextView) listItem.findViewById(R.id.textView_cookTime);
		cookTime.setText("Cook Time: " + Integer.toString(currentRecipe.getCookTime()));
		TextView serves = (TextView) listItem.findViewById(R.id.textView_serves);
		serves.setText("Serves: " + Integer.toString(currentRecipe.getServes()));

		return listItem;





	}
}
