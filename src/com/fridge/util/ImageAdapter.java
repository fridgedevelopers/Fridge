package com.fridge.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fridge.R;
import com.fridge.R.drawable;
import com.fridge.R.id;
import com.fridge.R.layout;
import com.fridge.database.FridgeDao;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private String category;
	private FridgeDao database;
	public String[] recipe_names;
	public String[] recipe_images_path;
	public String search;

	public ImageAdapter(Context context, String category) {
		this.context = context;
		this.category = category;
		this.database = new FridgeDao(this.context);
		this.recipe_names = database.RetrieveRecipeNames(this.category, search);
		this.recipe_images_path = database.RetrieveRecipeImages(this.category,
				search);
	}

	public ImageAdapter(Context context, String category, String search) {
		this.context = context;
		this.category = category;
		this.database = new FridgeDao(this.context);
		this.search = search;
		this.recipe_names = database.RetrieveRecipeNames(this.category, search);
		this.recipe_images_path = database.RetrieveRecipeImages(this.category,
				search);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.grid_item_layout, parent, false);
		} else {
			v = convertView;
		}
		TextView tv = (TextView) v.findViewById(R.id.grid_item_text);
		tv.setText(recipe_names[position]);

		ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
		try {
			AssetManager mngr = context.getAssets();
			InputStream is = mngr.open(recipe_images_path[position]);
			Bitmap bit = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bit);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageView.setScaleType(ScaleType.FIT_XY);
		return v;
	}

	@Override
	public int getCount() {
		return recipe_names.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// references to our images

}