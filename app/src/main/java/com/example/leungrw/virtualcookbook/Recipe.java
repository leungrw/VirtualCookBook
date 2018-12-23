package com.example.leungrw.virtualcookbook;

import java.util.ArrayList;

public class Recipe {
    private String recipeName;
    private int cookTime;
    private int serves;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private String rid;

    public Recipe()
    {
    	recipeName="Empty";
    	cookTime=0;
    	serves=0;
    	ingredients = new ArrayList<String>();
    	steps= new ArrayList<String>();
        rid="";
    }

    public Recipe(String recipeName, int cookTime, int serves, ArrayList<String> ingredients, ArrayList<String> steps, String rid)
    {
    	this.recipeName = recipeName;
    	this.cookTime = cookTime;
    	this.serves = serves;
    	this.ingredients = ingredients;
    	this.steps= steps;
        this.rid = rid;
    }



    public String getRecipeName() {

        return recipeName;
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getServes() {
        return serves;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public String getRid() {
        return rid;
    }



    public String getIngredientsString()
    {
        String ingredientsStr = "";
        for (String ingredient: this.ingredients)
        {
            ingredientsStr+=("\t\t"+ingredient+"\n");

        }
        return ingredientsStr;
    }

    public String getStepsString()
    {
        String stepsStr = "";
        int stepCount=0;
    	for (String step: this.steps)
        {
    		stepCount++;
    		stepsStr+=("\t\t"+Integer.toString(stepCount)+". "+ step +"\n");

    	}
        return stepsStr;
    }
    @Override
    public String toString() {
        String ingredientsStr = "";
        for (String ingredient: ingredients)
    	{
    		ingredientsStr+=("\t\t"+ingredient+"\n");

    	}

    	String stepsStr = "";
        int stepCount=0;
    	for (String step: steps)
        {
    		stepCount++;
    		stepsStr+=("\t\t"+Integer.toString(stepCount)+". "+ step +"\n");

    	}

        return recipeName + ":\nCook Time: " + cookTime + " minutes \nServes: "+ serves
        		+ "\nIngredients:\n" +ingredientsStr + "Steps:\n" + stepsStr;
    }

}
