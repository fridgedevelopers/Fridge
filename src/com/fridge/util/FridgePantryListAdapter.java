
package com.fridge.util;

import java.util.ArrayList;
import com.fridge.R;
import com.fridge.classes.Ingredient;
import com.fridge.classes.RecipeIngredient;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FridgePantryListAdapter extends ArrayAdapter<RecipeIngredient>
{
    private Activity context;

    private ArrayList<RecipeIngredient> modelList;

    private Typeface typeface;

    private int layout;

    public static class ViewHolder
    {
        protected TextView text;
        protected CheckBox checkbox;
    }

    public FridgePantryListAdapter(Activity context,
            int layout,
            ArrayList<RecipeIngredient> modelList,
            Typeface typeface)
    {
        super(context, layout, modelList);
        this.context = context;
        this.layout = layout;
        this.modelList = modelList;
        this.typeface = typeface;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = null;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
            view = inflater.inflate(layout, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.text.setText(modelList.get(position).getName());
            viewHolder.text.setTypeface(typeface);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox1);
            
            //-- Not working IDK WHY!!! :((
            //viewHolder.checkbox.setChecked(((ApplicationController)context.getApplicationContext()).add(modelList.get(position)));
            
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            RecipeIngredient element = (RecipeIngredient) viewHolder.checkbox.getTag();
                            element.setSelected(isChecked);
                            
                            if(element.isSelected())
                            	((ApplicationController) context.getApplicationContext()).add(element);
                            else
                            	((ApplicationController) context.getApplicationContext()).remove(element);
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(modelList.get(position));
        }
        else
        {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(modelList.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(modelList.get(position).getName());
        holder.checkbox.setChecked(modelList.get(position).isSelected());
        return view;
    }
}
