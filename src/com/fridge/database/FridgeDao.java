
package com.fridge.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fridge.classes.Category;
import com.fridge.classes.Ingredient;
import com.fridge.classes.Instruction;
import com.fridge.classes.Recipe;
import com.fridge.classes.RecipeIngredient;
import com.fridge.classes.Tag;
import com.fridge.database.FridgeContract.Favorites;
import com.fridge.database.FridgeContract.Instructions;
import com.fridge.database.FridgeContract.PantryCategories;
import com.fridge.database.FridgeContract.PantryIngredients;
import com.fridge.database.FridgeContract.RecipeCategories;
import com.fridge.database.FridgeContract.RecipeIngredients;
import com.fridge.database.FridgeContract.RecipeTags;
import com.fridge.database.FridgeContract.Recipes;
import com.fridge.util.IngredientsListAdapter;

public class FridgeDAO
{
    private FridgeHelper dbHelper;

    private SQLiteDatabase database;

    public FridgeDAO(Context context)
    {
        dbHelper = new FridgeHelper(context);
    }

    public FridgeDAO open() throws SQLException
    {
        database = dbHelper.openDataBase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public ArrayList<Tag> fetchTags(int id)
    {
        open();
        ArrayList<Tag> tags = new ArrayList<Tag>();
        String sql = "SELECT " + RecipeTags.COLUMN_RECIPE_ID + " FROM " + RecipeTags.TABLE_NAME + " WHERE "
                + RecipeTags.COLUMN_RECIPE_ID + " = " + id;
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Tag tag = new Tag(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor
                    .getColumnIndex("tag")));
            tags.add(tag);
        }

        cursor.close();
        close();
        return tags;
    }

    public ArrayList<Instruction> fetchInstructions(int id)
    {
        open();
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        String sql = "SELECT * FROM " + Instructions.TABLE_NAME + " WHERE "
                + Instructions.COLUMN_RECIPE_ID + " = " + id;
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Instruction instruction = new Instruction(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("instruction")));
            instructions.add(instruction);
        }

        cursor.close();
        close();
        return instructions;
    }
    
    public Recipe fetchRecipe(int id)
    {
        open();
        Recipe recipe = null;
        String sql = "SELECT r." + Recipes.COLUMN_RECIPE_ID + ", r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + ", r." + Recipes.COLUMN_NAME
                + " AS recipe_name, r." + Recipes.COLUMN_DESCRIPTION + " AS recipe_description, r."
                + Recipes.COLUMN_SERVING_SIZE + ", r." + Recipes.COLUMN_DURATION + ", r."
                + Recipes.COLUMN_VIEWS + ", r." + Recipes.COLUMN_RATINGS + ", r."
                + Recipes.COLUMN_OVERALL_RATING + ", rc." + RecipeCategories.COLUMN_NAME
                + " AS category_name, rc." + RecipeCategories.COLUMN_DESCRIPTION
                + " AS recipe_category_description" + ", r." + Recipes.COLUMN_IMAGE_PATH + " FROM "
                + Recipes.TABLE_NAME + " AS r" + " INNER JOIN " + RecipeCategories.TABLE_NAME
                + " AS rc" + " ON rc." + RecipeCategories.COLUMN_RECIPE_CATEGORY_ID + " = r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + " WHERE r." + Recipes.COLUMN_RECIPE_ID
                + " = '" + id + "' ORDER BY r." + Recipes.COLUMN_OVERALL_RATING + " ASC";
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category recipeCategory = new Category(cursor.getInt(cursor.getColumnIndex("rc_id")),
                    cursor.getString(cursor.getColumnIndex("category_name")),
                    cursor.getString(cursor.getColumnIndex("recipe_category_description")));
            recipe = new Recipe(cursor.getInt(cursor.getColumnIndex("_id")), recipeCategory,
                    cursor.getString(cursor.getColumnIndex("recipe_name")), cursor.getString(cursor
                            .getColumnIndex("recipe_description")), cursor.getString(cursor
                            .getColumnIndex("img_path")), cursor.getInt(cursor
                            .getColumnIndex("serving_size")), cursor.getInt(cursor
                            .getColumnIndex("duration")));
            recipe.setViews(cursor.getInt(cursor.getColumnIndex("views")));
            recipe.setRatings(cursor.getInt(cursor.getColumnIndex("ratings")));
            recipe.setOverallRating(cursor.getDouble(cursor.getColumnIndex("overall_rating")));
            recipe.setIngredients(fetchRecipeIngredients(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setInstructions(fetchInstructions(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setTags(fetchTags(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setFavorite(checkFavorites(id));
        }
        
        cursor.close();
        close();
        return recipe;
    }

    public ArrayList<Category> fetchRecipeCategories()
    {
        open();
        ArrayList<Category> recipeCategories = new ArrayList<Category>();
        String sql = "SELECT * FROM " + RecipeCategories.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category recipeCategory = new Category(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor
                            .getColumnIndex("description")));
            recipeCategories.add(recipeCategory);
        }
        cursor.close();
        close();
        return recipeCategories;
    }
    
    public long fetchIngredientId(String name)
    {
    	long id = -1;
    	open();
    	String sql = "SELECT " + Recipes.COLUMN_RECIPE_ID + " FROM " + Recipes.TABLE_NAME + " WHERE " + Recipes.COLUMN_NAME + " = '" + name + "'";
    	Cursor c = database.rawQuery(sql, null);
    	c.moveToFirst();
    	
    	if(! c.isAfterLast())
    		id = c.getLong(c.getColumnIndex("_id"));
    	
    	c.moveToNext();
    	c.close();
    	close();
    	return id;
    }

    public ArrayList<Recipe> fetchRecipes()
    {
        open();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        String sql = "SELECT r." + Recipes.COLUMN_RECIPE_ID + ", r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + ", r." + Recipes.COLUMN_NAME
                + " AS recipe_name, r." + Recipes.COLUMN_DESCRIPTION + " AS recipe_description, r."
                + Recipes.COLUMN_SERVING_SIZE + ", r." + Recipes.COLUMN_DURATION + ", r."
                + Recipes.COLUMN_VIEWS + ", r." + Recipes.COLUMN_RATINGS + ", r."
                + Recipes.COLUMN_OVERALL_RATING + ", rc." + RecipeCategories.COLUMN_NAME
                + " AS category_name, rc." + RecipeCategories.COLUMN_DESCRIPTION
                + " AS recipe_category_description" + ", r." + Recipes.COLUMN_IMAGE_PATH + " FROM "
                + Recipes.TABLE_NAME + " AS r" + " INNER JOIN " + RecipeCategories.TABLE_NAME
                + " AS rc" + " ON rc." + RecipeCategories.COLUMN_RECIPE_CATEGORY_ID + " = r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + " WHERE r." + Recipes.COLUMN_OVERALL_RATING
                + " > 0" + " ORDER BY r." + Recipes.COLUMN_OVERALL_RATING + " ASC";
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category recipeCategory = new Category(cursor.getInt(cursor.getColumnIndex("rc_id")),
                    cursor.getString(cursor.getColumnIndex("category_name")),
                    cursor.getString(cursor.getColumnIndex("recipe_category_description")));
            Recipe recipe = new Recipe(cursor.getInt(cursor.getColumnIndex("_id")), recipeCategory,
                    cursor.getString(cursor.getColumnIndex("recipe_name")), cursor.getString(cursor
                            .getColumnIndex("recipe_description")), cursor.getString(cursor
                            .getColumnIndex("img_path")), cursor.getInt(cursor
                            .getColumnIndex("serving_size")), cursor.getInt(cursor
                            .getColumnIndex("duration")));
            recipe.setViews(cursor.getInt(cursor.getColumnIndex("views")));
            recipe.setRatings(cursor.getInt(cursor.getColumnIndex("ratings")));
            recipe.setOverallRating(cursor.getDouble(cursor.getColumnIndex("overall_rating")));
            recipe.setIngredients(fetchRecipeIngredients(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setInstructions(fetchInstructions(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setTags(fetchTags(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipes.add(recipe);
        }
        cursor.close();
        close();
        return recipes;
    }
    
    public ArrayList<Recipe> fetchAllRecipes()
    {
        open();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        String sql = "SELECT r." + Recipes.COLUMN_RECIPE_ID + ", r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + ", r." + Recipes.COLUMN_NAME
                + " AS recipe_name, r." + Recipes.COLUMN_DESCRIPTION + " AS recipe_description, r."
                + Recipes.COLUMN_SERVING_SIZE + ", r." + Recipes.COLUMN_DURATION + ", r."
                + Recipes.COLUMN_VIEWS + ", r." + Recipes.COLUMN_RATINGS + ", r."
                + Recipes.COLUMN_OVERALL_RATING + ", rc." + RecipeCategories.COLUMN_NAME
                + " AS category_name, rc." + RecipeCategories.COLUMN_DESCRIPTION
                + " AS recipe_category_description" + ", r." + Recipes.COLUMN_IMAGE_PATH + " FROM "
                + Recipes.TABLE_NAME + " AS r" + " INNER JOIN " + RecipeCategories.TABLE_NAME
                + " AS rc" + " ON rc." + RecipeCategories.COLUMN_RECIPE_CATEGORY_ID + " = r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID;
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category recipeCategory = new Category(cursor.getInt(cursor.getColumnIndex("rc_id")),
                    cursor.getString(cursor.getColumnIndex("category_name")),
                    cursor.getString(cursor.getColumnIndex("recipe_category_description")));
            Recipe recipe = new Recipe(cursor.getInt(cursor.getColumnIndex("_id")), recipeCategory,
                    cursor.getString(cursor.getColumnIndex("recipe_name")), cursor.getString(cursor
                            .getColumnIndex("recipe_description")), cursor.getString(cursor
                            .getColumnIndex("img_path")), cursor.getInt(cursor
                            .getColumnIndex("serving_size")), cursor.getInt(cursor
                            .getColumnIndex("duration")));
            recipe.setViews(cursor.getInt(cursor.getColumnIndex("views")));
            recipe.setRatings(cursor.getInt(cursor.getColumnIndex("ratings")));
            recipe.setOverallRating(cursor.getDouble(cursor.getColumnIndex("overall_rating")));
            recipe.setIngredients(fetchRecipeIngredients(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setInstructions(fetchInstructions(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setTags(fetchTags(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipes.add(recipe);
        }
        cursor.close();
        close();
        return recipes;
    }
    
    public ArrayList<Recipe> fetchRecipes(String category)
    {
        open();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        String sql = "SELECT r." + Recipes.COLUMN_RECIPE_ID + ", r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + ", r." + Recipes.COLUMN_NAME
                + " AS recipe_name, r." + Recipes.COLUMN_DESCRIPTION + " AS recipe_description, r."
                + Recipes.COLUMN_SERVING_SIZE + ", r." + Recipes.COLUMN_DURATION + ", r."
                + Recipes.COLUMN_VIEWS + ", r." + Recipes.COLUMN_RATINGS + ", r."
                + Recipes.COLUMN_OVERALL_RATING + ", rc." + RecipeCategories.COLUMN_NAME
                + " AS category_name, rc." + RecipeCategories.COLUMN_DESCRIPTION
                + " AS recipe_category_description" + ", r." + Recipes.COLUMN_IMAGE_PATH + " FROM "
                + Recipes.TABLE_NAME + " AS r" + " INNER JOIN " + RecipeCategories.TABLE_NAME
                + " AS rc" + " ON rc." + RecipeCategories.COLUMN_RECIPE_CATEGORY_ID + " = r."
                + Recipes.COLUMN_RECIPE_CATEGORY_ID + " WHERE rc." + RecipeCategories.COLUMN_NAME
                + " = '" + category + "' ORDER BY r." + Recipes.COLUMN_OVERALL_RATING + " ASC";
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category recipeCategory = new Category(cursor.getInt(cursor.getColumnIndex("rc_id")),
                    cursor.getString(cursor.getColumnIndex("category_name")),
                    cursor.getString(cursor.getColumnIndex("recipe_category_description")));
            Recipe recipe = new Recipe(cursor.getInt(cursor.getColumnIndex("_id")), recipeCategory,
                    cursor.getString(cursor.getColumnIndex("recipe_name")), cursor.getString(cursor
                            .getColumnIndex("recipe_description")), cursor.getString(cursor
                            .getColumnIndex("img_path")), cursor.getInt(cursor
                            .getColumnIndex("serving_size")), cursor.getInt(cursor
                            .getColumnIndex("duration")));
            recipe.setViews(cursor.getInt(cursor.getColumnIndex("views")));
            recipe.setRatings(cursor.getInt(cursor.getColumnIndex("ratings")));
            recipe.setOverallRating(cursor.getDouble(cursor.getColumnIndex("overall_rating")));
            recipe.setIngredients(fetchRecipeIngredients(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setInstructions(fetchInstructions(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipe.setTags(fetchTags(cursor.getInt(cursor.getColumnIndex("_id"))));
            recipes.add(recipe);
        }
        cursor.close();
        close();
        return recipes;
    }

    public ArrayList<Category> fetchPantryCategories()
    {
        open();
        ArrayList<Category> pantryCategories = new ArrayList<Category>();
        String sql = "SELECT * FROM " + PantryCategories.TABLE_NAME;
        Log.i("Pantry Category SQL", sql);
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Category pantryCategory = new Category(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor
                            .getColumnIndex("description")));
            pantryCategories.add(pantryCategory);
        }
        cursor.close();
        close();
        return pantryCategories;
    }

    public ArrayList<RecipeIngredient> fetchPantryIngredients(String category)
    {
        open();
        ArrayList<RecipeIngredient> pantryIngredients = new ArrayList<RecipeIngredient>();
        String sql = "SELECT * FROM " + PantryCategories.TABLE_NAME + " INNER JOIN "
                + PantryIngredients.TABLE_NAME + " ON " + PantryCategories.TABLE_NAME + "."
                + PantryCategories.COLUMN_NAME + "=" + "'" + category + "' AND "
                + PantryIngredients.TABLE_NAME + "." + PantryIngredients.COLUMN_PANTRY_CATEGORY_ID
                + "=" + PantryCategories.TABLE_NAME + "."
                + PantryCategories.COLUMN_PANTRY_CATEGORY_ID;
        Log.i("Pantry Ingredients SQL", sql);
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            RecipeIngredient pantryIngredient = new RecipeIngredient(cursor.getLong(cursor
                    .getColumnIndex("pc_id")), cursor.getString(cursor.getColumnIndex("name")));
            pantryIngredients.add(pantryIngredient);
        }
        cursor.close();
        close();
        return pantryIngredients;
    }

    

    public String[] RetrieveRecipeNames(String category, String search, int fave)
    {
        open();
        Cursor category_id = database.rawQuery("SELECT * FROM " + RecipeCategories.TABLE_NAME
                + " WHERE " + RecipeCategories.COLUMN_NAME + " LIKE " + "'%" + category + "%'",
                null);

        category_id.moveToFirst();

        ArrayList<String> mArrayList = new ArrayList<String>();
        Cursor resultset;
        if(search == null)
        {
            if(fave == 0)
            {
                resultset = database.rawQuery(
                        "SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "
                                + Recipes.COLUMN_RECIPE_CATEGORY_ID + "="
                                + category_id.getInt(category_id.getColumnIndex("_id")), null);
            }
            else
            {
                resultset = database.rawQuery(
                        "SELECT * FROM " + Recipes.TABLE_NAME + " INNER JOIN "
                                + Favorites.TABLE_NAME + " ON " + Recipes.TABLE_NAME + "."
                                + Recipes.COLUMN_RECIPE_CATEGORY_ID + "="
                                + category_id.getInt(category_id.getColumnIndex("_id")) + " AND "
                                + Recipes.TABLE_NAME + "." + Recipes.COLUMN_RECIPE_ID + "="
                                + Favorites.TABLE_NAME + "." + Favorites.COLUMN_RECIPE_ID, null);
            }
        }
        else
        {
            if(fave == 0)
            {
                resultset = database.rawQuery(
                        "SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "
                                + Recipes.COLUMN_RECIPE_CATEGORY_ID + "="
                                + category_id.getInt(category_id.getColumnIndex("_id")) + " AND "
                                + Recipes.COLUMN_NAME + " LIKE " + "'%" + search + "%'", null);
            }
            else
            {
                resultset = database.rawQuery(
                        "SELECT * FROM " + Recipes.TABLE_NAME + " INNER JOIN "
                                + Favorites.TABLE_NAME + " ON " + Recipes.TABLE_NAME + "."
                                + Recipes.COLUMN_RECIPE_CATEGORY_ID + "="
                                + category_id.getInt(category_id.getColumnIndex("_id")) + " AND "
                                + Recipes.COLUMN_NAME + " LIKE " + "'%" + search + "%'" + " AND "
                                + Recipes.TABLE_NAME + "." + Recipes.COLUMN_RECIPE_ID + "="
                                + Favorites.TABLE_NAME + "." + Favorites.COLUMN_RECIPE_ID, null);
            }

        }

        for(resultset.moveToFirst(); !resultset.isAfterLast(); resultset.moveToNext())
        {
            mArrayList.add(resultset.getString(resultset.getColumnIndex("name")));
        }

        resultset.close();
        category_id.close();
        close();
        return mArrayList.toArray(new String[mArrayList.size()]);
    }

    public String[] RetrieveRecipeImages(String category, String search, int fave)
    {
        open();
        Cursor category_id = database.rawQuery("SELECT * FROM " + RecipeCategories.TABLE_NAME
                + " WHERE " + RecipeCategories.COLUMN_NAME + " LIKE " + "'%" + category + "%'",
                null);
        category_id.moveToFirst();

        ArrayList<String> mArrayList = new ArrayList<String>();
        Cursor resultset;

        if(search == null)
        {
            if(fave == 0)
            {
                resultset = database.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0), null);
                Log.i("Query", "SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0));
            }
            else
            {
                resultset = database.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME
                        + " INNER JOIN " + Favorites.TABLE_NAME + " ON " + Recipes.TABLE_NAME + "."
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND "
                        + Recipes.TABLE_NAME + "." + Recipes.COLUMN_RECIPE_ID + "="
                        + Favorites.TABLE_NAME + "." + Favorites.COLUMN_RECIPE_ID, null);
                Log.i("Query", "SELECT * FROM " + Recipes.TABLE_NAME + " INNER JOIN "
                        + Favorites.TABLE_NAME + " ON " + Recipes.TABLE_NAME + "."
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND "
                        + Recipes.TABLE_NAME + "." + Recipes.COLUMN_RECIPE_ID + "="
                        + Favorites.TABLE_NAME + "." + Favorites.COLUMN_RECIPE_ID);
            }
        }
        else
        {
            if(fave == 0)
            {
                resultset = database.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME + " WHERE "
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND "
                        + Recipes.COLUMN_NAME + " LIKE " + "'%" + search + "%'", null);
            }
            else
            {
                resultset = database.rawQuery("SELECT * FROM " + Recipes.TABLE_NAME
                        + " INNER JOIN " + Favorites.TABLE_NAME + " ON " + Recipes.TABLE_NAME + "."
                        + Recipes.COLUMN_RECIPE_CATEGORY_ID + "=" + category_id.getInt(0) + " AND "
                        + Recipes.COLUMN_NAME + " LIKE " + "'%" + search + "%'" + " AND "
                        + Recipes.TABLE_NAME + "." + Recipes.COLUMN_RECIPE_ID + "="
                        + Favorites.TABLE_NAME + "." + Favorites.COLUMN_RECIPE_ID, null);
            }
        }

        for(resultset.moveToFirst(); !resultset.isAfterLast(); resultset.moveToNext())
        {
            mArrayList.add(resultset.getString(resultset.getColumnIndex("img_path")));

        }
        resultset.close();
        category_id.close();
        // this.closeDB();
        close();
        return mArrayList.toArray(new String[mArrayList.size()]);
    }

    public ArrayList<RecipeIngredient> fetchRecipeIngredients(int r_id)
    {
        open();
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<RecipeIngredient>();
        String sql = "SELECT * FROM " + RecipeIngredients.TABLE_NAME + " INNER JOIN "
                + PantryIngredients.TABLE_NAME + " ON " + RecipeIngredients.TABLE_NAME + "."
                + RecipeIngredients.COLUMN_RECIPE_ID + "=" + r_id + " AND "
                + PantryIngredients.TABLE_NAME + "."
                + PantryIngredients.COLUMN_PANTRY_INGREDIENT_ID + "="
                + RecipeIngredients.TABLE_NAME + "."
                + RecipeIngredients.COLUMN_PANTRY_INGREDIENT_ID;
        Log.i("Recipe Ingredients SQL", sql);
        Cursor cursor = database.rawQuery(sql, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            RecipeIngredient recipeIngredient = new RecipeIngredient(cursor.getLong(cursor
                    .getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getDouble(cursor.getColumnIndex("qty")), cursor.getString(cursor
                            .getColumnIndex("unit")), cursor.getString(cursor
                            .getColumnIndex("notes")));
            recipeIngredients.add(recipeIngredient);
        }
        cursor.close();
        close();
        return recipeIngredients;
    }

    public Boolean checkFavorites(int id)
    {
        open();
        Boolean ret = false;
        Cursor mCursor = database.rawQuery("SELECT * FROM " + Favorites.TABLE_NAME + " WHERE "
                + Favorites.COLUMN_RECIPE_ID + "=" + id, null);
        mCursor.moveToFirst();

        if(mCursor.getCount() != 0) ret = true;
        mCursor.close();
        close();
        return ret;
    }

    public void insertFavorites(int id)
    {
        open();
        ContentValues values = new ContentValues();
        values.put(Favorites.COLUMN_RECIPE_ID, id);
        database.insert(Favorites.TABLE_NAME, null, values);
        close();
    }

    public void removeFavorite(int id)
    {
        open();
        ContentValues values = new ContentValues();
        values.put(Favorites.COLUMN_RECIPE_ID, id);
        database.delete(Favorites.TABLE_NAME, Favorites.COLUMN_RECIPE_ID + "=" + id, null);
        close();
    }

    public boolean InsertPantryIngredients(String value, String category)
    {
        open();
        Boolean ret = false;
        Cursor cursor = database.rawQuery("SELECT * FROM " + PantryCategories.TABLE_NAME
                + " WHERE " + PantryCategories.COLUMN_NAME + "=" + "'" + category + "'", null);
        cursor.moveToFirst();

        String sql = "SELECT * FROM " + PantryIngredients.TABLE_NAME + " WHERE LOWER("
                + PantryIngredients.COLUMN_NAME + ")" + "='" + value.toLowerCase() + "'";
        Cursor check = database.rawQuery(sql, null);
        if(check.getCount() == 0)
        {
            ContentValues values = new ContentValues();
            values.put(PantryIngredients.COLUMN_NAME, value);
            values.put(PantryIngredients.COLUMN_PANTRY_CATEGORY_ID, cursor.getInt(0));
            database.insert(PantryIngredients.TABLE_NAME, null, values);
            ret = true;
        }
        Log.i("insert ingredient SQL", sql);
        check.close();
        cursor.close();
        close();
        return ret;
    }
}
