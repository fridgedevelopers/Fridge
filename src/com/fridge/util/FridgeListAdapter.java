package com.fridge.util;

import java.util.List;

import com.fridge.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FridgeListAdapter extends ArrayAdapter<String> {
	private Context context;
	private String[] values;
	private Typeface typeface;
	private int layout;
	private List<String> list;

	public FridgeListAdapter(Context context, int layout, String[] values,
			Typeface typeface) {
		super(context, layout, values);
		this.context = context;
		this.layout = layout;
		this.values = values;
		this.typeface = typeface;
	}

	public FridgeListAdapter(Context context, int layout, List<String> list,
			Typeface typeface) {
		super(context, layout, list);
		this.context = context;
		this.layout = layout;
		this.list = list;
		this.typeface = typeface;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, parent, false);
		TextView textView = (TextView) view.findViewById(R.id.label);
		textView.setText(values[position]);
		textView.setTypeface(typeface);

		return view;
	}

}
