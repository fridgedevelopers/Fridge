package com.fridge.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.fridge.classes.Ingredient;
import com.fridge.classes.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashSet;

public class ApplicationController extends Application
{
	private ArrayList<RecipeIngredient> recommendables;
	
	private SharedPreferences	preferences;

	public static final String	GET_RECOMMENDATION_TYPE		= "com.fridge.constants.RECOMMENDATION_TYPE";

	public static final String	APPLICATON_URL				= "http://localhost/fridge/";

	public static final String	EXTERNAL_STORAGE_LOCATON	= "";

	@Override
	public void onCreate()
	{
		super.onCreate();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		recommendables = new ArrayList<RecipeIngredient>();
	}

	public SharedPreferences getPreferences()
	{
		return preferences;
	}

	public ArrayList<RecipeIngredient> getRecommendables()
	{
		return recommendables;
	}
	
	public boolean add(RecipeIngredient ingredient)
	{
		return recommendables.add(ingredient);
	}
	
	public boolean remove(RecipeIngredient ingredient)
	{
		return recommendables.remove(ingredient);
	}
	
	public boolean removeAllRecommendables()
	{
		return recommendables.removeAll(recommendables);
	}
}
