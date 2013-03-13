
package com.fridge.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fridge.R;
import com.fridge.classes.Category;
import com.fridge.database.FridgeDAO;
import com.fridge.util.ImageAdapter;

public class Favorites extends Activity implements OnItemSelectedListener
{
    private FridgeDAO database;

    private ProgressDialog pDialog;

    Spinner spinner;

    EditText search;

    int isFav = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        database = new FridgeDAO(this);
        new LoadCategoriesTask().execute();

        search = (EditText) findViewById(R.id.recipe_category_search);
        TextWatcher textWatcher = new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                new LoadRecipesTask().execute();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        };

        search.addTextChangedListener(textWatcher);

    }

    public void setCategoriesList(String[] DBcategories)
    {

        String[] categories = DBcategories;

        spinner = (Spinner) findViewById(R.id.spinner_recipe_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_layout,
                R.id.spinner_text, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                new LoadRecipesTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

    }

    public void setFavoriteRecipesList(String[] recipe_names, String[] recipe_images_path)
    {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        //gridview.setAdapter(new ImageAdapter(Favorites.this, recipe_names, recipe_images_path));

        gridview.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), SingleRecipe.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra("category", spinner.getSelectedItem().toString());
                startActivity(i);
            }
        });
    }

    class LoadRecipesTask extends AsyncTask<String, String, String>
    {

        String[] recipe_names;

        String[] recipe_images_path;

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(Favorites.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            try
            {
                recipe_names = database.RetrieveRecipeNames(spinner.getSelectedItem().toString(),
                        search.getText().toString(), isFav);
                recipe_images_path = database.RetrieveRecipeImages(spinner.getSelectedItem()
                        .toString(), search.getText().toString(), isFav);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.e("Exception:", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            setFavoriteRecipesList(recipe_names, recipe_images_path);

        }
    }

    class LoadCategoriesTask extends AsyncTask<String, String, String>
    {

        String[] categories;

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(Favorites.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            try
            {
                ArrayList<Category> c = database.fetchRecipeCategories();
                String[] categories = new String[c.size()];

                for(int i = 0; i < c.size(); i++)
                    categories[i] = c.get(i).toString();

                this.categories = categories;

            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.e("Exception:", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            setCategoriesList(categories);

        }
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home :
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected((android.view.MenuItem) item);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
    {
        Toast.makeText(Favorites.this, "pic" + position, Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        // AdapterView adapter = (AdapterView) parent.getItemAtPosition(pos);
        Toast.makeText(this, id + " selected", Toast.LENGTH_SHORT).show();
        // setFridgeList(pos);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {

        }
        else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

        }
    }

}
