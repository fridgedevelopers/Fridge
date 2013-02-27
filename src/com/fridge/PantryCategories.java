package com.fridge;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fridge.util.FridgeListAdapter;

public class PantryCategories extends ListActivity
{
	private EditText editText;
	private String[] categories;
	private FridgeListAdapter adapter;
	private Typeface typeface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantry_categories);

		typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster.ttf");
		categories = new String[]
				{"Vegetables", "Fruits", "Spices", "Meat", "Fish", "Pork", "Beef" };
		adapter = new FridgeListAdapter(this, R.layout.row_layout, categories, typeface);
		setListAdapter(adapter);
		
		editText = (EditText) findViewById(R.id.recipe_category_search);
		editText.addTextChangedListener(
			new TextWatcher()
			{
				@Override
				public void afterTextChanged(Editable arg0)
				{
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					adapter.getFilter().filter(s);
				}
			}
		);
		
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id)
	{
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, Pantry.class);
		intent.putExtra("position", position);
		intent.putExtra("item", item);
		startActivityForResult(intent, 0);
	}
	

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_recipe_categories, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
