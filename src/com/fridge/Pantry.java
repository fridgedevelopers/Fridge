package com.fridge;

import com.fridge.util.FridgeListAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Pantry extends ListActivity implements OnItemSelectedListener , OnItemClickListener{
	
	private String[] categories;
	private FridgeListAdapter listAdapter;
	private Typeface typeface;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantry);

//		Intent intent = getIntent();
//		int position = intent.getIntExtra("position", -1);
//		String item = (String) intent.getStringExtra("item");

		String[] DayOfWeek = {"VEGETABLES", "FRUITS", "PORK", "BEEF", "SPICES", "OTHERS"};
		Spinner spinner = (Spinner)findViewById(R.id.spinner_pantry_categories);
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_layout, R.id.spinner_text, DayOfWeek);
	    adapter.setDropDownViewResource(R.layout.spinner_item_layout);
	    spinner.setAdapter(adapter);
		
		
//		//Spinner
//		Spinner spinner = (Spinner) findViewById(R.id.spinner_pantry_categories);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.pantry_categories, android.R.layout.simple_spinner_item);
//		
//		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
//		spinner.setAdapter(adapter);
//		spinner.setOnItemSelectedListener(this);
	    
		//initialize list
		setFridgeList(0);
	}
	
	public void setFridgeList(int pos)
	{
		//List

		categories = new String [] {"Vegetables", "Fruits", "Pork", "Beef", "Spices", "Seasoning", "Liquids", "Others" };
		
		String[] Vegetables = new String [] { "Pechay", "Cabbage", "Potato", "Carrots", "Pumpkin", "Cabbage", "Potato", "Carrots", "Pumpkin"};		
		//String[] list = categories[pos];
		
		typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster.ttf");
		listAdapter = new FridgeListAdapter(this, R.layout.chcklist_layout, Vegetables, typeface);
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
