package com.fridge.activity;

import com.fridge.database.FridgeDao;
import com.fridge.util.ImageAdapter;
import com.fridge.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

public class Favorites extends Activity implements OnItemSelectedListener {
	private FridgeDao database;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		database = new FridgeDao(this);

		String[] categories = database.RetrieveRecipeCategories();
		final Spinner spinner = (Spinner) findViewById(R.id.spinner_recipe_categories);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_text_layout, R.id.spinner_text, categories);
		adapter.setDropDownViewResource(R.layout.spinner_item_layout);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				GridView gridview = (GridView) findViewById(R.id.gridview);
				gridview.setAdapter(new ImageAdapter(Favorites.this, spinner
						.getSelectedItem().toString()));
				gridview.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						// Sending image id to FullScreenActivity
						Intent i = new Intent(getApplicationContext(),
								SingleRecipe.class);
						// passing array index
						i.putExtra("id", position);
						i.putExtra("category", spinner.getSelectedItem()
								.toString());
						startActivity(i);
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		final EditText search = (EditText) findViewById(R.id.recipe_category_search);
		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1,
					int i2) {
				String searchString = search.getText().toString();
				GridView gridview = (GridView) findViewById(R.id.gridview);
				gridview.setAdapter(new ImageAdapter(Favorites.this, spinner
						.getSelectedItem().toString(), searchString));
				gridview.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						// Sending image id to FullScreenActivity
						Intent i = new Intent(getApplicationContext(),
								SingleRecipe.class);
						// passing array index
						i.putExtra("id", position);
						i.putExtra("category", spinner.getSelectedItem()
								.toString());
						startActivity(i);
					}
				});
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		};

		search.addTextChangedListener(textWatcher);

		// Spinner
		// Spinner spinner = (Spinner)
		// findViewById(R.id.spinner_recipe_categories);
		// ArrayAdapter<CharSequence> adapter =
		// ArrayAdapter.createFromResource(this, R.array.recipe_categories,
		// android.R.layout.simple_spinner_item);
		//
		// adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		// spinner.setAdapter(adapter);
		// spinner.setOnItemSelectedListener(this);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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

		return super.onOptionsItemSelected((android.view.MenuItem) item);
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Toast.makeText(Favorites.this, "pic" + position, Toast.LENGTH_SHORT)
				.show();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)

		// AdapterView adapter = (AdapterView) parent.getItemAtPosition(pos);
		Toast.makeText(this, id + " selected", Toast.LENGTH_SHORT).show();
		// setFridgeList(pos);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

}
