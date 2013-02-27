package com.fridge;

import com.fridge.util.ImageAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SingleRecipe extends FragmentActivity{
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
 
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.single_recipe_image);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
        
        TextView titleView = (TextView) findViewById(R.id.recipe_description);
        titleView.setText("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..." + position);
        
    }
 
}