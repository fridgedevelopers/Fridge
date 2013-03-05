package com.fridge.database;

import java.util.ArrayList;
import java.util.List;

import com.fridge.database.DatabaseClasses.*;
import com.fridge.database.FridgeContract.Favorites;
import com.fridge.database.FridgeContract.Instructions;
import com.fridge.database.FridgeContract.PantryCategories;
import com.fridge.database.FridgeContract.PantryIngredients;
import com.fridge.database.FridgeContract.RecipeCategories;
import com.fridge.database.FridgeContract.RecipeImages;
import com.fridge.database.FridgeContract.RecipeIngredients;
import com.fridge.database.FridgeContract.RecipeTags;
import com.fridge.database.FridgeContract.Recipes;
import com.fridge.database.FridgeContract.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class FridgeDao {
	private final Context context;
	private SQLiteDatabase db;
	private FridgeHelper helper;
	
	public FridgeDao(Context ctx){
		this.context = ctx;
		helper = new FridgeHelper(context);
	}
	
	// OPERATIONS //
	
	public FridgeDao open() throws SQLException
	{
		db = helper.getWritableDatabase();
		return this;
	}
	
	public void InsertPantryCategories(String name)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(PantryCategories.COLUMN_NAME, name);
		values.put(PantryCategories.COLUMN_DESCRIPTION,"Ingredients that does not belong to any categories mentioned.");
		db.insert(PantryCategories.TABLE_NAME, null, values);
		db.close();
	}
	
	public void InsertRecipeCategories()
	{
		this.open();
		ContentValues values = new ContentValues();
		String[] names= {"","FingerFood","Seafood","Soup","Main Dish"};
		for(int i=1;i<=4;i++)
		{
			values.put(RecipeCategories.COLUMN_NAME, names[i]);
			values.put(RecipeCategories.COLUMN_DESCRIPTION,"A dish that can be best eaten using the fingers.");
			db.insert(RecipeCategories.TABLE_NAME, null, values);
		}
		
		db.close();
	}
	
	public void InsertPantryIngredients(String value, String category)
	{
		this.open();
//		db.execSQL(PantryIngredients.DROP_TABLE);
//		db.execSQL(PantryIngredients.CREATE_TABLE);
		Cursor category_id = db.rawQuery("SELECT * FROM " + PantryCategories.TABLE_NAME + " WHERE "+ PantryCategories.COLUMN_NAME + "=" + "'"+ category + "'", null);
		category_id.moveToFirst();
		ContentValues values = new ContentValues();
		values.put(PantryIngredients.COLUMN_NAME,value);
		values.put(PantryIngredients.COLUMN_PANTRY_CATEGORY_ID, category_id.getInt(0));
		db.insert(PantryIngredients.TABLE_NAME, null, values);
		db.close();
	}
	
	public void InsertRecipe()
	{
		this.open();
		ContentValues values = new ContentValues();
//		db.execSQL(PantryIngredients.DROP_TABLE);
//		db.execSQL(PantryIngredients.CREATE_TABLE);
		for(int i=1; i<=4;i++)
		{
			values.put(Recipes.COLUMN_NAME,"FingerFood"+i);
			values.put(Recipes.COLUMN_RECIPE_CATEGORY_ID,1);
			values.put(Recipes.COLUMN_DESCRIPTION,"FingerFoodD"+i);
			values.put(Recipes.COLUMN_DURATION,1);
			values.put(Recipes.COLUMN_SERVING_SIZE,2);
			values.put(Recipes.COLUMN_VIEWS,3);
			values.put(Recipes.COLUMN_RATINGS,4);
			values.put(Recipes.COLUMN_OVERALL_RATING,5);
			
			db.insert(Recipes.TABLE_NAME, null, values);
		}
		
		
		db.close();
	}
	
	public void InsertRecipeImages()
	{
		this.open();
//		db.execSQL(PantryIngredients.DROP_TABLE);
//		db.execSQL(PantryIngredients.CREATE_TABLE);
		ContentValues values = new ContentValues();
		for(int i=5; i<=8;i++)
		{
		values.put(RecipeImages.COLUMN_RECIPE_ID,i);
		values.put(RecipeImages.COLUMN_PATH,"images/S"+i+".jpg");

		db.insert(RecipeImages.TABLE_NAME, null, values);
		}
		db.close();
	}
	
	
	public void InsertFavorites(int id)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(Favorites.COLUMN_RECIPE_ID, id);
		db.insert(Favorites.TABLE_NAME, null, values);
		db.close();
	}
	
	public void DeleteFavorites(int id)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(Favorites.COLUMN_RECIPE_ID, id);
		db.delete(Favorites.TABLE_NAME, Favorites.COLUMN_RECIPE_ID+"="+id, null);
		db.close();
	}
	
	public Boolean CheckFavorites(int id)
	{
		this.open();
		Boolean ret = false;
		Cursor mCursor = db.rawQuery("SELECT * FROM "+ Favorites.TABLE_NAME+" WHERE "+ Favorites.COLUMN_RECIPE_ID+"="+id, null);
		mCursor.moveToFirst(); 
		
		if(mCursor.getCount()!=0)
			ret=true;
		db.close();
		return ret;
	}
	
	public String[] RetrieveRecipeDescription(String recipe_name)
	{
		this.open();
		
		Cursor mCursor = db.rawQuery("SELECT "+ Recipes.COLUMN_DESCRIPTION +","+ Recipes.COLUMN_RECIPE_ID + " FROM "+ Recipes.TABLE_NAME + " WHERE "+ Recipes.COLUMN_NAME + "='" + recipe_name+"'", null);
		mCursor.moveToFirst(); 
		String ret[] = {mCursor.getString(0),mCursor.getString(1)};
		db.close();
		return ret;
	}
	
	public String[] RetrievePantryCategories()
	{
		this.open();
		ArrayList<String> mArrayList = new ArrayList<String>();
		Cursor mCursor = db.rawQuery("SELECT * FROM "+ PantryCategories.TABLE_NAME, null);
		
		for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
		    mArrayList.add(mCursor.getString(1));
		}
		
		db.close();
		return mArrayList.toArray(new String[mArrayList.size()]);
	}
	
	public String[] RetrieveRecipeCategories()
	{
		this.open();
		ArrayList<String> mArrayList = new ArrayList<String>();
		Cursor mCursor = db.rawQuery("SELECT * FROM "+ RecipeCategories.TABLE_NAME, null);
		
		for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
		    mArrayList.add(mCursor.getString(1));
		}
		mCursor.close();
		db.close();
		return mArrayList.toArray(new String[mArrayList.size()]);
	}
	

	public String[] RetrievePantryIngredients(String category)
	{
		this.open();
		Cursor category_id = db.rawQuery("SELECT * FROM " + PantryCategories.TABLE_NAME + " WHERE "+ PantryCategories.COLUMN_NAME + "=" + "'"+ category + "'", null);
		category_id.moveToFirst();
		
		ArrayList<String> mArrayList = new ArrayList<String>();
		Cursor resultset = db.rawQuery("SELECT * FROM " + PantryIngredients.TABLE_NAME + " WHERE "+ PantryIngredients.COLUMN_PANTRY_CATEGORY_ID + "=" + category_id.getInt(0), null);
	
		for(resultset.moveToFirst(); !resultset.isAfterLast(); resultset.moveToNext()) {
		    mArrayList.add(resultset.getString(3));
		}
		db.close();
		return mArrayList.toArray(new String[mArrayList.size()]);
	}
	
	public String[] RetrieveRecipeNames(String category, String search)
	{
		this.open();
		Cursor category_id = db.rawQuery("SELECT * FROM " + RecipeCategories.TABLE_NAME + " WHERE "+ RecipeCategories.COLUMN_NAME + " LIKE " + "'%"+ category + "%'", null);
		
		category_id.moveToFirst();
		
		ArrayList<String> mArrayList = new ArrayList<String>();
		Cursor resultset;
		if(search==null){
			 resultset = db.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "+ Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0), null);
		}
		else{
			 resultset = db.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "+ Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND " + Recipes.COLUMN_NAME + " LIKE " + "'%"+ search + "%'", null);
		}
			for(resultset.moveToFirst(); !resultset.isAfterLast(); resultset.moveToNext()) {
		    mArrayList.add(resultset.getString(2));
		}
			resultset.close();
			category_id.close();
		db.close();
		return mArrayList.toArray(new String[mArrayList.size()]);
	}
	
	public String[] RetrieveRecipeImages(String category,String search)
	{
		this.open();
		Cursor category_id = db.rawQuery("SELECT * FROM " + RecipeCategories.TABLE_NAME + " WHERE "+ RecipeCategories.COLUMN_NAME + " LIKE " + "'%"+ category + "%'", null);
		category_id.moveToFirst();
		
		ArrayList<String> mArrayList = new ArrayList<String>();
		Cursor resultset;
		
		if(search==null){
			resultset = db.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "+ Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0), null);
		}
		else{
			 resultset = db.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "+ Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND " + Recipes.COLUMN_NAME + " LIKE " + "'%"+ search + "%'", null);
		}
		for(resultset.moveToFirst(); !resultset.isAfterLast(); resultset.moveToNext()) {
			Cursor imagepath = db.rawQuery("SELECT * FROM "+ RecipeImages.TABLE_NAME + " WHERE "+ RecipeImages.COLUMN_RECIPE_ID + "=" + resultset.getInt(0), null);
			for(imagepath.moveToFirst(); !imagepath.isAfterLast(); imagepath.moveToNext()) {
			    mArrayList.add(imagepath.getString(2));
				imagepath.close();
			}
		}
		resultset.close();
		category_id.close();
		db.close();
		return mArrayList.toArray(new String[mArrayList.size()]);
	}
	
	public void Update(){
		this.open();
		
		for(int i=5, j=1; i<=8; i++,j++)
		{
			ContentValues args = new ContentValues();
			args.put(RecipeImages.COLUMN_PATH, "images/S"+j+".jpg");
			db.update(RecipeImages.TABLE_NAME, args, RecipeImages.COLUMN_RECIPE_IMAGES_ID + "=" + i, null);
		}
		
	}	
	
	// START OF FRIDGE HELPER // 
	public class FridgeHelper extends SQLiteOpenHelper
	{
		public static final int DATABASE_VERSION = 7;
		public static final String DATABASE_NAME = "fridge.db";
		
		public FridgeHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(RecipeCategories.CREATE_TABLE);
			db.execSQL(PantryCategories.CREATE_TABLE);
			db.execSQL(Recipes.CREATE_TABLE);
			db.execSQL(Favorites.CREATE_TABLE);
			db.execSQL(RecipeImages.CREATE_TABLE);
			db.execSQL(RecipeTags.CREATE_TABLE);
			db.execSQL(Settings.CREATE_TABLE);
			db.execSQL(PantryIngredients.CREATE_TABLE);
			db.execSQL(RecipeIngredients.CREATE_TABLE);
			db.execSQL(Instructions.CREATE_TABLE);
		
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL(RecipeCategories.DROP_TABLE);
			db.execSQL(Recipes.DROP_TABLE);
			db.execSQL(Favorites.DROP_TABLE);
			db.execSQL(RecipeImages.DROP_TABLE);
			db.execSQL(RecipeTags.DROP_TABLE);
			db.execSQL(Settings.DROP_TABLE);
			db.execSQL(PantryIngredients.DROP_TABLE);
			db.execSQL(RecipeIngredients.DROP_TABLE);
			db.execSQL(Instructions.DROP_TABLE);
			onCreate(db);
		}	
	} //end of FRIDGE HELPER
}
