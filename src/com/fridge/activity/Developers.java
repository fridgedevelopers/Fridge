package com.fridge.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fridge.R;



public class Developers extends ListActivity implements OnItemClickListener {
	
	int MAX = 5;
	// Array of strings storing country names
	String[] Names = new String[] { 
			"Vince",
			"Yana",
			"Arjay",
			"Jezza",
			"David" };

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] Pics = new int[] { 
			R.drawable.vince,
			R.drawable.yana,
			R.drawable.arjay,
			R.drawable.mae,
			R.drawable.owen};

	// Array of strings to store currencies
	String[] Description = new String[] {
			"https://facebook.com/vinceedgarnoel",
			"https://facebook.com/yanabasamartinez",
			"https://facebook.com/arjay.osma",
			"https://facebook.com/mae.lee.02",
			"https://facebook.com/saibarru"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_developers);
		
		   // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();        
        
        for(int i=0;i<MAX;i++){
        	HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name",  Names[i]);
            hm.put("description", Description[i]);
            hm.put("pics", Integer.toString(Pics[i]) );            
            aList.add(hm);        
        }
        
        // Keys used in Hashmap
        String[] from = { "pics","name","description" };
        
        // Ids of views in listview_layout
        int[] to = { R.id.pic,R.id.name,R.id.description};        
        
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.developer_list_layout, from, to);
        
        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(android.R.id.list);
        
        // Setting the adapter to the listView
        listView.setAdapter(adapter);

	}


	@Override
	protected void onListItemClick(ListView listView, View view, int position,long id){
		
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
