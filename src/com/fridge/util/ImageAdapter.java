package com.fridge.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fridge.R;
import com.fridge.classes.Recipe;
import com.fridge.database.FridgeDAO;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter
{
	private Context				context;

	private ArrayList<Recipe>	recipes;

	public String				search	= null;

	public int					favorites;		// 1 - IF favorite 0-IF NOT from favorite

	public ImageAdapter(Context context, ArrayList<Recipe> recipes)
	{
		this.context = context;
		new FridgeDAO(this.context);
		this.recipes = recipes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v;

		if (convertView == null)
		{
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.grid_item_layout, parent, false);
		}
		else
			v = convertView;

		TextView tv = (TextView) v.findViewById(R.id.grid_item_text);
		tv.setText(recipes.get(position).getName());
		ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

		try
		{
			AssetManager mngr = context.getAssets();
			InputStream is = mngr.open(recipes.get(position).getImagePath());
			Bitmap bit = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bit);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		imageView.setScaleType(ScaleType.FIT_XY);
		return v;
	}

	@Override
	public int getCount()
	{
		return recipes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return recipes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return recipes.get(position).getId();
	}
}