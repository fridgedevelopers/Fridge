package com.fridge.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import com.fridge.database.FridgeDao;
import com.fridge.R;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class Main extends FragmentActivity
{
	FridgeDao db = new FridgeDao(this);
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db.InsertRecipeCategories();
		db.InsertRecipe();
//		db.InsertRecipeImages();
//		db.Update();
		
//		try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String currentDBPath = "//data//com.fridge//databases//fridge.db";
//                String backupDBPath = "fridge.db";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//
//                if (currentDB.exists()) {
//                    FileChannel src = new FileInputStream(currentDB).getChannel();
//                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                }
//            }
//        } catch (Exception e) {
//
//        }
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
