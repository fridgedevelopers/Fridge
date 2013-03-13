package com.fridge.database;

import android.provider.BaseColumns;

public class FridgeContract
{
	/* Private Constructor */
	private FridgeContract()
	{
		// it does nothing...
	}
	
	public static abstract class RecipeCategories implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "recipe_categories";
		public static final String COLUMN_RECIPE_CATEGORY_ID = "_id";			//-- Recipe Category ID
		public static final String COLUMN_NAME = "name";						//-- Recipe Category Name
		public static final String COLUMN_DESCRIPTION = "description";			//-- Recipe Category Description
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
					COLUMN_RECIPE_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					COLUMN_NAME + " TEXT NOT NULL," +
					COLUMN_DESCRIPTION + " TEXT NOT NULL" +
				");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class PantryCategories implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "pantry_categories";
		public static final String COLUMN_PANTRY_CATEGORY_ID = "_id";			//-- Pantry Category ID
		public static final String COLUMN_NAME = "name";						//-- Pantry Category Name
		public static final String COLUMN_DESCRIPTION = "description";			//-- Pantry Category Description
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
					COLUMN_PANTRY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					COLUMN_NAME + " TEXT NOT NULL," +
					COLUMN_DESCRIPTION + " TEXT NOT NULL" +
				");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class Recipes implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "recipes";						
		public static final String COLUMN_RECIPE_ID = "_id";					//-- Recipe ID
		public static final String COLUMN_RECIPE_CATEGORY_ID = "rc_id";			//-- Recipe Category ID, used as foreign key
		public static final String COLUMN_NAME = "name";						//-- Recipe Name
		public static final String COLUMN_DESCRIPTION = "description";			//-- Recipe Description, used in recommendation
		public static final String COLUMN_SERVING_SIZE = "serving_size";		//-- Recipe Serving Size, the size of the serving
		public static final String COLUMN_DURATION = "duration";				//-- Recipe Duration (this will be in minutes only)
		public static final String COLUMN_VIEWS = "views";						//-- Recipe Views, used in recommendation
		public static final String COLUMN_RATINGS = "ratings";					//-- Recipe Ratings, used in recommendation
		public static final String COLUMN_OVERALL_RATING = "overall_rating";	//-- Recipe Overall Rating, based from collected data from different users, used in recommendation
        public static final String COLUMN_IMAGE_PATH = "img_path";
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
				COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_RECIPE_CATEGORY_ID + " INTEGER NOT NULL," +
				COLUMN_NAME + " TEXT NOT NULL," +
				COLUMN_DESCRIPTION + " TEXT NOT NULL," +
				COLUMN_SERVING_SIZE + " INTEGER NOT NULL," +
				COLUMN_DURATION + " INTEGER NOT NULL," +
				COLUMN_VIEWS + " INTEGER NOT NULL," +
				COLUMN_RATINGS + " INTEGER NOT NULL," +
				COLUMN_OVERALL_RATING + " REAL NOT NULL," +
                COLUMN_IMAGE_PATH + " TEXT NOT NULL," +
				" FOREIGN KEY (" + COLUMN_RECIPE_CATEGORY_ID + ")" +
				" REFERENCES " + RecipeCategories.TABLE_NAME + "(" + COLUMN_RECIPE_CATEGORY_ID + ")" +
				" ON UPDATE CASCADE" +
			");";
	
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class Instructions implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "instructions";
		public static final String COLUMN_INSTRUCTIONS_ID = "_id";				//-- Favorites ID
		public static final String COLUMN_RECIPE_ID = "r_id";					//-- Favorites ID
		public static final String COLUMN_INSTRUCTION = "instruction";			//-- Recipe ID, used as a foreign key
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE " + TABLE_NAME + "(" +
				COLUMN_INSTRUCTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_RECIPE_ID + " INTEGER NOT NULL," +
				COLUMN_INSTRUCTION + " TEXT NOT NULL," +
				" FOREIGN KEY (" + COLUMN_RECIPE_ID + ")" +
				" REFERENCES " + Recipes.TABLE_NAME + "(" + COLUMN_RECIPE_ID + ")" +
				" ON UPDATE CASCADE" +
			");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class Favorites implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "favorites";
		public static final String COLUMN_FAVORITES_ID = "_id";				//-- Favorites ID
		public static final String COLUMN_RECIPE_ID = "r_id";					//-- Recipe ID, used as a foreign key
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
				COLUMN_FAVORITES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_RECIPE_ID + " INTEGER NOT NULL," +
				" FOREIGN KEY (" + COLUMN_RECIPE_ID + ")" +
				" REFERENCES " + Recipes.TABLE_NAME + "(" + COLUMN_RECIPE_ID + ")" +
				" ON UPDATE CASCADE" +
			");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class RecipeTags implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "recipe_tags";
		public static final String COLUMN_RECIPE_TAG_ID = "_id";				//-- Recipe Tag ID
		public static final String COLUMN_RECIPE_ID = "r_id";					//-- Recipe ID, used as a foreign key
		public static final String COLUMN_TAG = "tag";							//-- Recipe Tag

		/* Create Table Command */
		public static final String CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
				COLUMN_RECIPE_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_RECIPE_ID + " INTEGER NOT NULL," +
				COLUMN_TAG + " TEXT NOT NULL," +
				" FOREIGN KEY (" + COLUMN_RECIPE_ID + ")" +
				" REFERENCES " + Recipes.TABLE_NAME + "(" + COLUMN_RECIPE_ID + ")" +
				" ON UPDATE CASCADE" +
			");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class PantryIngredients implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "pantry_ingredients";
		public static final String COLUMN_PANTRY_INGREDIENT_ID = "_id";		//-- Pantry Ingredient ID
		public static final String COLUMN_PANTRY_CATEGORY_ID = "pc_id";			//-- Pantry Category ID, used as a foreign key
		public static final String COLUMN_UNIT_ID = "un_id";					//-- Unit ID, used as a foreign key
		public static final String COLUMN_NAME = "name";						//-- Pantry Ingredient Name
		
		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
				COLUMN_PANTRY_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_UNIT_ID + " INTEGER NOT NULL," +
				COLUMN_PANTRY_CATEGORY_ID + " INTEGER NOT NULL," +
				COLUMN_NAME + " TEXT NOT NULL," +
				" FOREIGN KEY (" + COLUMN_PANTRY_CATEGORY_ID + ")" +
				" REFERENCES " + PantryCategories.TABLE_NAME + "(" + COLUMN_PANTRY_CATEGORY_ID + ")" +
			");";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class RecipeIngredients implements BaseColumns
	{
		/* Table MetaInformation */
		public static final String TABLE_NAME = "recipe_ingredients";
		public static final String COLUMN_RECIPE_INGREDIENTS_ID = "_id";		//-- Recipe Ingredient ID
		public static final String COLUMN_RECIPE_ID = "r_id";					//-- Recipe ID, used as a foreign key
		public static final String COLUMN_PANTRY_INGREDIENT_ID = "pi_id";		//-- Recipe Pantry Ingredient ID, used as a foreign key
		public static final String COLUMN_QUANTITY = "qty";						//-- Recipe Ingredient Quantity
		public static final String COLUMN_NOTES = "notes";						//-- Recipe Ingredient Notes

		/* Create Table Command */
		public static final String CREATE_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
				COLUMN_RECIPE_INGREDIENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_RECIPE_ID + " INTEGER NOT NULL," +
				COLUMN_PANTRY_INGREDIENT_ID + " INTEGER NOT NULL," +
				COLUMN_QUANTITY + " REAL NOT NULL," +
				COLUMN_NOTES + " TEXT NOT NULL," +
				" FOREIGN KEY (" + COLUMN_RECIPE_ID + ")" +
				" REFERENCES " + Recipes.TABLE_NAME + "(" + COLUMN_RECIPE_ID + ")," +
				" FOREIGN KEY (" + COLUMN_PANTRY_INGREDIENT_ID + ")" +
				" REFERENCES " + PantryIngredients.TABLE_NAME + "(" + COLUMN_PANTRY_INGREDIENT_ID + ")" +
				" ON UPDATE CASCADE" +
			")";
		
		/* Drop Table Command */
		public static final String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
}
