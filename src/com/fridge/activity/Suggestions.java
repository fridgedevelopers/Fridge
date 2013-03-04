package com.fridge.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fridge.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Suggestions extends ListActivity implements OnItemClickListener {
	
	// Array of strings storing country names
	String[] countries = new String[] { "India", "Pakistan", "Sri Lanka",
			"China", "Bangladesh", "Nepal", "Afghanistan", "North Korea",
			"South Korea", "Japan" };

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] flags = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };

	// Array of strings to store currencies
	String[] currency = new String[] { "Indian Rupee", "Pakistani Rupee",
			"Sri Lankan Rupee", "Renminbi", "Bangladeshi Taka",
			"Nepalese Rupee", "Afghani", "North Korean Won",
			"South Korean Won", "Japanese Yen" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestions);
		
		   // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();        
        
        for(int i=0;i<10;i++){
        	HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt",  countries[i]);
            hm.put("cur", currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );            
            aList.add(hm);        
        }
        
        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };
        
        // Ids of views in listview_layout
        int[] to = { R.id.recipe_image,R.id.recipe_title,R.id.recipe_description};        
        
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.suggestions_list_layout, from, to);
        
        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(android.R.id.list);
        
        // Setting the adapter to the listView
        listView.setAdapter(adapter);

	}


	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
//		String item = (String) getListAdapter().getItem(position);
//		// Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
//		AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//		alert.setTitle("Recipe");
//		alert.setMessage(item);
//		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				// do whatever here
//			}
//		});
//
//		alert.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int whichButton) {
//						// Canceled.
//					}
//				});
//
//		alert.show();
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
