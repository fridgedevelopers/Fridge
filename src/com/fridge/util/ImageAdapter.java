package com.fridge.util;
 
import com.fridge.R;
import com.fridge.R.drawable;
import com.fridge.R.id;
import com.fridge.R.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

 
public class ImageAdapter extends BaseAdapter {
	
	private Context context;
       
	public ImageAdapter(Context context) {
		this.context = context;
	}
 
	public View getView(int position, View convertView, ViewGroup parent){
        // TODO Auto-generated method stub
        View v;
        if(convertView==null)
        {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.grid_item_layout, parent,false);
        }else{
            v = convertView;
        }
        TextView tv = (TextView)v.findViewById(R.id.grid_item_text);
        tv.setText("Food "+position);
        
        ImageView iv = (ImageView)v.findViewById(R.id.grid_item_image);
        iv.setScaleType(ScaleType.FIT_XY);
        iv.setImageResource(mThumbIds[position]);

        return v;
    }
	
 
	@Override
	public int getCount() {
		return mThumbIds.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}

	

    // references to our images
    public Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_8,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_8,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_8,
            R.drawable.sample_6, R.drawable.sample_7
    };
    
}