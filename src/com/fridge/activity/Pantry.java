package com.fridge.activity;

import com.fridge.R;
import com.fridge.database.FridgeDao;
import com.fridge.util.FridgeListAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Pantry extends ListActivity implements OnItemSelectedListener , OnItemClickListener{
	
	private String[] categories;
	private FridgeListAdapter listAdapter;
	private Typeface typeface;
	private FridgeDao database;
	private Spinner spinner;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantry);
		database = new FridgeDao(this);

//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.search_recipe_category);
//        BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.main_header_repeat);
//        bg.setTileModeX(TileMode.REPEAT);
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            layout.setBackgroundDrawable(bg);
        
//		Intent intent = getIntent();
//		int position = intent.getIntExtra("position", -1);
//		String item = (String) intent.getStringExtra("item");
		
		spinner = (Spinner)findViewById(R.id.spinner_pantry_categories);
		String[] pantrycategories = database.RetrievePantryCategories();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_layout, R.id.spinner_text, pantrycategories);
	    adapter.setDropDownViewResource(R.layout.spinner_item_layout);
	    spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setFridgeList(0);	
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		//		//Spinner
//		Spinner spinner = (Spinner) findViewById(R.id.spinner_pantry_categories);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.pantry_categories, android.R.layout.simple_spinner_item);
//		
//		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
//		spinner.setAdapter(adapter);
//		spinner.setOnItemSelectedListener(this);
	    
		//initialize list
	}
	
	public void setFridgeList(int pos)
	{
		//List
		String[] ingredients = database.RetrievePantryIngredients(spinner.getSelectedItem().toString());
		typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster.ttf");
		listAdapter = new FridgeListAdapter(this, R.layout.chcklist_layout, ingredients, typeface);
		setListAdapter(listAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id)
	{
		String item = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Recipe");
		alert.setMessage(item);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  //do whatever here
			  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int whichButton) {
			   // Canceled.
			 }
		});

		alert.show();
	}
	
	
	public void addIngredient(View view)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Add Ingredient");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  database.InsertPantryIngredients(value, spinner.getSelectedItem().toString());
		 //database.Update();
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}
	
	public void onItemSelected(AdapterView<?> parent, View view,int pos, long id)
	{
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		
		//	AdapterView adapter = (AdapterView) parent.getItemAtPosition(pos);
		Toast.makeText(this, id + " selected", Toast.LENGTH_SHORT).show();
		//setFridgeList(pos);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
