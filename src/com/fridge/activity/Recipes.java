package com.fridge.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fridge.R;
import com.fridge.classes.Category;
import com.fridge.classes.Recipe;
import com.fridge.database.FridgeDAO;
import com.fridge.util.ImageAdapter;

public class Recipes extends Activity implements OnItemSelectedListener
{
	private GridView			gridview;
	
	private FridgeDAO			database;

	private ProgressDialog		pDialog;

	private Spinner				spinner;

	private EditText			search;

	private ArrayList<Category>	categories;

	private ArrayList<Recipe>	recipes;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes);
		database = new FridgeDAO(this);
		new LoadCategoriesTask().execute();
		search = (EditText) findViewById(R.id.recipe_category_search);
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1,
					int i2)
			{
				new LoadRecipesTask().execute();
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		};

		search.addTextChangedListener(textWatcher);
	}

	public void setCategoriesList(ArrayList<Category> categories)
	{
		spinner = (Spinner) findViewById(R.id.spinner_recipe_categories);
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
				R.layout.spinner_text_layout, R.id.spinner_text, categories);
		adapter.setDropDownViewResource(R.layout.spinner_item_layout);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{
				new LoadRecipesTask().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{}
		});

	}

	public void setRecipesList(ArrayList<Recipe> recipes)
	{
		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(Recipes.this, recipes));
		gridview.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				Recipe recipe = (Recipe) gridview.getAdapter().getItem(position);
				Intent i = new Intent(getApplicationContext(),
						SingleRecipe.class);
				i.putExtra("recipeID", recipe.getId());
				startActivity(i);
			}
		});
	}

	class LoadRecipesTask extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(Recipes.this);
			pDialog.setMessage("Loading");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args)
		{
			try
			{
				recipes = database.fetchRecipes(spinner.getSelectedItem()
						.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Log.e("Exception:", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			pDialog.dismiss();
			setRecipesList(recipes);
		}
	}

	class LoadCategoriesTask extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(Recipes.this);
			pDialog.setMessage("Loading");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args)
		{
			try
			{
				categories = database.fetchRecipeCategories();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			pDialog.dismiss();
			setCategoriesList(categories);
		}
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
		Toast.makeText(Recipes.this, "pic" + position, Toast.LENGTH_SHORT)
				.show();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id)
	{
		Toast.makeText(this, id + " selected", Toast.LENGTH_SHORT).show();
	}

	public void onNothingSelected(AdapterView<?> parent)
	{}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{}
		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{}
	}

}
