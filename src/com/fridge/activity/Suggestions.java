package com.fridge.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fridge.R;
import com.fridge.classes.Ingredient;
import com.fridge.classes.Recipe;
import com.fridge.classes.RecipeIngredient;
import com.fridge.database.FridgeDAO;
import com.fridge.util.ApplicationController;
import com.fridge.util.ImageAdapter;

public class Suggestions extends Activity
{
	private GridView			gridview;

	private FridgeDAO			database;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestions);
		database = new FridgeDAO(this);
		ArrayList<Recipe> recommendedRecipes = new ArrayList<Recipe>();
		Intent intent = getIntent();
		String recommendationType = intent
				.getStringExtra(ApplicationController.GET_RECOMMENDATION_TYPE);
		//ArrayList<String> ingredientString = (ArrayList<String>) intent.getStringArrayListExtra("ingredients");
		ArrayList<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
		ingredients = ((ApplicationController) getApplicationContext()).getRecommendables();
		
//		for(int i = 0; i < ingredientString.size(); i++)
//			ingredients.add(new RecipeIngredient(database.fetchIngredientId(ingredientString.get(i)), ingredientString.get(i)));
		
		recommendedRecipes = (recommendationType
				.equals("System Recommendation")) ? getSystemRecommendation()
				: getUserRecommendation(ingredients);
				
		setRecipesList(recommendedRecipes);
	}

	public void setRecipesList(ArrayList<Recipe> recipes)
	{
		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(Suggestions.this, recipes));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				Recipe recipe = (Recipe) gridview.getAdapter()
						.getItem(position);
				Intent i = new Intent(getApplicationContext(),
						SingleRecipe.class);
				i.putExtra("recipeID", recipe.getId());
				startActivity(i);
			}
		});
	}

	private ArrayList<Recipe> getSystemRecommendation()
	{
		ArrayList<Recipe> recipes = database.fetchRecipes();
		return recipes;
	}

	private ArrayList<Recipe> getUserRecommendation(ArrayList<RecipeIngredient> ingredients)
	{
		double[] percs = null;
		ArrayList<Recipe> allRecipes = database.fetchAllRecipes();
		ArrayList<Recipe> recommendations = new ArrayList<Recipe>();
		Iterator<Recipe> iterator = allRecipes.iterator();
		percs = new double[allRecipes.size()];
		int j = 0;
		
		while(iterator.hasNext())
		{
			Recipe recipe = iterator.next();
			ArrayList<RecipeIngredient> universal = new ArrayList<RecipeIngredient>();
			universal.addAll(recipe.getIngredients());
			universal.addAll(ingredients);
			Iterator<RecipeIngredient> i = recipe.getIngredients().iterator();
			int count = 0;
			
			while(i.hasNext())
			{
				String str = i.next().toString();
				
				if(ingredients.toString().contains(str))
					count++;
			}
			
			double perc = count / universal.size() * 100;
			percs[j++] = perc;

			Log.i("Universal Size", String.valueOf(universal.size()));
			Log.i("Count", String.valueOf(count));
			Log.i("Percentage", String.valueOf(perc));
			
			if(count > 0)
				recommendations.add(recipe);
		}
		
		return recommendations;
	}
}
