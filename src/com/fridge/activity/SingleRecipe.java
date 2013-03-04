package com.fridge.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fridge.R;
import com.fridge.util.ExpandableListViewAdapter;
import com.fridge.util.ImageAdapter;
import com.fridge.database.FridgeDao;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;

public class SingleRecipe extends ExpandableListActivity implements OnChildClickListener {

	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();
	private FridgeDao database;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        database = new FridgeDao(this);
        // get intent data
        Intent i = getIntent();

        // Selected image id
        final int position = i.getExtras().getInt("id");
        String category = i.getExtras().getString("category");
        final ImageAdapter imageAdapter = new ImageAdapter(this, category);
        ImageView imageView = (ImageView) findViewById(R.id.single_recipe_image);
        
        try {
        	AssetManager mngr = this.getAssets();
            InputStream is = mngr.open(imageAdapter.recipe_images_path[position]);
            Bitmap bit = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(bit);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        TextView recipe_name = (TextView) findViewById(R.id.main_recipe_title);
        recipe_name.setText(imageAdapter.recipe_names[position]);
        
        final String recipe_details[] = database.RetrieveRecipeDescription(imageAdapter.recipe_names[position]);
        TextView titleView = (TextView) findViewById(R.id.recipe_description);
        titleView.setText(recipe_details[0]);
        
        //Check checkbox if favorite already
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite_checkbox);
        favorite.setChecked(database.CheckFavorites(Integer.parseInt(recipe_details[1])));
        
        //Checkbox listener
        favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (((CheckBox) view).isChecked()) {
					database.InsertFavorites(Integer.parseInt(recipe_details[1]));
		  			Toast.makeText(SingleRecipe.this,
		  					"Marked as favorite.", Toast.LENGTH_SHORT).show();
				}
				else{
					database.DeleteFavorites(Integer.parseInt(recipe_details[1]));
					Toast.makeText(SingleRecipe.this,
		  					"Unmarked as favorite.", Toast.LENGTH_SHORT).show();
				}	
			}
      	});

		ExpandableListView expandbleLis = getExpandableListView();
		expandbleLis.setDividerHeight(1);
		expandbleLis.setGroupIndicator(null);
		expandbleLis.setClickable(true);
		
		
		setGroupData();
		setChildGroupData();

		ExpandableListViewAdapter mNewAdapter = new ExpandableListViewAdapter(groupItem, childItem);
		mNewAdapter
				.setInflater(
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						this);
		getExpandableListView().setAdapter(mNewAdapter);
		expandbleLis.setOnChildClickListener(this);

	}

	public void setGroupData() {
		groupItem.add("Ingredients");
		groupItem.add("Procedures");
	}

	public void setChildGroupData() {
		/**
		 * Add Data For TecthNology
		 */
		ArrayList<String> child = new ArrayList<String>();
		child = new ArrayList<String>();
		child.add("Android");
		child.add("Window Mobile");
		child.add("iPHone");
		child.add("Blackberry");
		childItem.add(child);
		/**
		 * Add Data For Mobile
		 */
		child = new ArrayList<String>();
		child.add("Android");
		child.add("eque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci v");
		child.add("eque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci v");
		child.add("eque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci v");
		childItem.add(child);

		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(SingleRecipe.this, "Clicked On Child",
				Toast.LENGTH_SHORT).show();
		return true;
	}

	public void showRatingDialog() {
		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
		final RatingBar rating = new RatingBar(this);
		rating.setMax(5);
		rating.setNumStars(2);

		popDialog.setIcon(android.R.drawable.btn_star_big_on);
		popDialog.setTitle("Rate this recipe");
		popDialog.setView(rating);

		// Button OK
		popDialog.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// txtView.setText(String.valueOf(rating.getProgress()));
						dialog.dismiss();
					}

				})

		// Button Cancel
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		popDialog.create();
		popDialog.show();

	}

}