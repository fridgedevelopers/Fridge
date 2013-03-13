package com.fridge.activity;

import java.util.ArrayList;
import java.util.HashSet;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fridge.R;
import com.fridge.classes.Category;
import com.fridge.classes.Ingredient;
import com.fridge.classes.RecipeIngredient;
import com.fridge.database.FridgeDAO;
import com.fridge.util.ApplicationController;
import com.fridge.util.FridgePantryListAdapter;

public class Pantry extends ListActivity implements OnItemSelectedListener,
		OnItemClickListener
{
	private FridgePantryListAdapter	listAdapter;

	private Typeface				typeface;

	private FridgeDAO				database;

	private Spinner					spinnerCategories;

	private ProgressDialog			pDialog;

	private ArrayList<RecipeIngredient>	ingredients;

	private ArrayList<Category>		categories;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantry);
		database = new FridgeDAO(this);
		new LoadCategoriesTask().execute();
	}

	public void setCategoriesList(ArrayList<Category> pantryCategories)
	{
		String[] pc = new String[pantryCategories.size()];

		for (int i = 0; i < pantryCategories.size(); i++)
			pc[i] = pantryCategories.get(i).getName();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_text_layout, R.id.spinner_text, pc);
		adapter.setDropDownViewResource(R.layout.spinner_item_layout);
		spinnerCategories = (Spinner) findViewById(R.id.spinner_pantry_categories);
		spinnerCategories.setAdapter(adapter);
		spinnerCategories
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{
						new LoadIngredientsTask().execute();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{

					}
				});

	}

	public void setIngredientsList(ArrayList<RecipeIngredient> ingredients)
	{
		typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster.ttf");
		listAdapter = new FridgePantryListAdapter(this,
				R.layout.chcklist_layout, ingredients, typeface);
		setListAdapter(listAdapter);
	}

	class LoadCategoriesTask extends AsyncTask<String, String, String>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(Pantry.this);
			pDialog.setMessage("Opening cabinets...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args)
		{
			try
			{
				categories = database.fetchPantryCategories();
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

	class LoadIngredientsTask extends AsyncTask<String, String, String>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(Pantry.this);
			pDialog.setMessage("Counting sheeps...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args)
		{
			try
			{
				ingredients = database.fetchPantryIngredients(spinnerCategories
						.getSelectedItem().toString());
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
			setIngredientsList(ingredients);
		}
	}

	public void addIngredient(View view)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Add Ingredient");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				String value = input.getText().toString();
				if (value.length() != 0)
				{
					if (database.InsertPantryIngredients(value,
							spinnerCategories.getSelectedItem().toString()))
					{
						Toast.makeText(Pantry.this, value + " added.",
								Toast.LENGTH_SHORT).show();
						new LoadIngredientsTask().execute();
					}
					else
						Toast.makeText(Pantry.this,
								value + " is already on the list.",
								Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(Pantry.this, "Provide input.",
							Toast.LENGTH_SHORT).show();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton)
					{

					}
				});

		alert.show();
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id)
	{}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id)
	{}

	public void onNothingSelected(AdapterView<?> parent)
	{}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{}

	public void click(View view)
	{
		Toast.makeText(this, "Opening the cabinets...", Toast.LENGTH_SHORT)
				.show();
		ArrayList<RecipeIngredient> recommendables = ((ApplicationController) getApplicationContext())
				.getRecommendables();
		//Log.i("Recommendables", recommendables.toString());

		if (recommendables.size() > 0)
		{
			Intent intent = new Intent(this, Suggestions.class);
			intent.putExtra("com.fridge.constants.RECOMMENDATION_TYPE", "Not System Recommendation");
			//intent.putExtra("ingredients", recommendables);
			startActivity(intent);
		}
		else
			Toast.makeText(this, "You selected nothing... :(",
					Toast.LENGTH_SHORT).show();
	}
}
