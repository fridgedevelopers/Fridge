package com.fridge.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fridge.R;
import com.fridge.classes.Instruction;
import com.fridge.classes.Recipe;
import com.fridge.classes.RecipeIngredient;
import com.fridge.database.FridgeDAO;
import com.fridge.util.ExpandableListViewAdapter;

public class SingleRecipe extends ExpandableListActivity implements
		OnChildClickListener
{
	private Recipe				recipe;

	private ArrayList<String>	groupItem;

	private ArrayList<Object>	childItem;

	private FridgeDAO			database;

	int							position;

	int							isFav	= 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recipe);
		database = new FridgeDAO(this);
		groupItem = new ArrayList<String>();
		childItem = new ArrayList<Object>();
		Intent intent = getIntent();
		long id = intent.getExtras().getLong("recipeID");
		recipe = database.fetchRecipe(Integer.parseInt(String.valueOf(id)));
		setRecipeDetails();
		setExpandableList();
	}

	public void setExpandableList()
	{
		ExpandableListView expandbleLis = getExpandableListView();
		expandbleLis.setDividerHeight(1);
		expandbleLis.setGroupIndicator(null);
		expandbleLis.setClickable(true);

		groupItem.add("Ingredients");
		groupItem.add("Procedures");

		ArrayList<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
		ingredients = recipe.getIngredients();
		ArrayList<String> recipeIngredients = new ArrayList<String>();
		
		Log.i("INFO", recipeIngredients.toString());

		for (RecipeIngredient recipeIngredient : ingredients)
			recipeIngredients.add(recipeIngredient != null ? recipeIngredient
					.toString() : null);

		childItem.add(recipeIngredients);

		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		instructions = recipe.getInstructions();

		ArrayList<String> recipeInstructions = new ArrayList<String>();
		for (Instruction instruction : instructions)
			recipeInstructions.add(instruction != null ? instruction.toString()
					: null);

		childItem.add(recipeInstructions);
		ExpandableListViewAdapter mNewAdapter = new ExpandableListViewAdapter(
				groupItem, childItem);
		mNewAdapter
				.setInflater(
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						this);
		getExpandableListView().setAdapter(mNewAdapter);
		expandbleLis.setOnChildClickListener(this);
	}

	public void setRecipeDetails()
	{
		ImageView imageView = (ImageView) findViewById(R.id.single_recipe_image);

		try
		{
			AssetManager mngr = this.getAssets();
			InputStream is = mngr.open(recipe.getImagePath());
			Bitmap bit = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bit);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		RatingBar rb = (RatingBar) findViewById(R.id.vince);
		rb.setRating(Float.parseFloat(String.valueOf(recipe.getRatings())));

		TextView recipe_name = (TextView) findViewById(R.id.main_recipe_title);
		recipe_name.setText(recipe.getName());

		TextView titleView = (TextView) findViewById(R.id.recipe_description);
		titleView.setText(String.valueOf(recipe.getDuration()));

		TextView prepTime = (TextView) findViewById(R.id.recipe_preprtime);
		prepTime.setText(recipe.getDuration() + " mins.");

		CheckBox favorite = (CheckBox) findViewById(R.id.favorite_checkbox);
		favorite.setChecked(recipe.isFavorite());
		favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				if (((CheckBox) view).isChecked())
				{
					database.insertFavorites(Integer.parseInt(String
							.valueOf(recipe.getId())));
					Toast.makeText(SingleRecipe.this, "Marked as favorite.",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					database.removeFavorite(Integer.parseInt(String
							.valueOf(recipe.getId())));
					Toast.makeText(SingleRecipe.this, "Unmarked as favorite.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id)
	{
		Toast.makeText(SingleRecipe.this, "Clicked On Child",
				Toast.LENGTH_SHORT).show();
		return true;
	}
}