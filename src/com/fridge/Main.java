package com.fridge;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class Main extends FragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	
	public void recipesClick(View view)
	{
		Intent intent = new Intent(this, Recipes.class);
		startActivity(intent);
	}
	
	public void pantryClick(View view)
	{
		Intent intent = new Intent(this, Pantry.class);
		startActivity(intent);
	}
	
	public void suggestionsClick(View view)
	{
		Intent intent = new Intent(this, Suggestions.class);
		startActivity(intent);
	}
	
	public void favoritesClick(View view)
	{
		Intent intent = new Intent(this, Favorites.class);
		startActivity(intent);
	}
}
