
package com.fridge.activity;

import java.io.IOException;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.fridge.R;
import com.fridge.database.FridgeHelper;
import com.fridge.util.ApplicationController;

public class Main extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FridgeHelper myDbHelper = new FridgeHelper(this);

        try
        {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = null;
        
        switch(item.getItemId())
        {
            case R.id.menu_settings :
                intent = new Intent(this, Settings.class);
                break;
            case R.id.developers :
                intent = new Intent(this, Developers.class);
                break;
        }

        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void click(View view)
    {
        Intent intent = null;
        
        switch(view.getId())
        {
            case R.id.btn_pantry:
                intent = new Intent(this, Pantry.class);
                break;
            case R.id.btn_recipes:
                intent = new Intent(this, Recipes.class);
                break;
            case R.id.btn_suggestions:
                intent = new Intent(this, Suggestions.class);
                intent.putExtra(ApplicationController.GET_RECOMMENDATION_TYPE, "System Recommendation");
                break;
            case R.id.btn_favorites:
                intent = new Intent(this, Favorites.class);
                break;
        }
        
        startActivity(intent);
    }
}
