package com.fridge;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientsStepsFragment extends ListFragment implements
		LoaderCallbacks<Void> {

	private static final String TAG = "FragmentTabs";

	private String mTag;
	private MyAdapter mAdapter;
	private ArrayList<String> mItems;
	private LayoutInflater mInflater;
	private int mTotalLength;
	private int mPosition;

	private static final String[] INGREDIENTS = new String[] { 
		"3 cups / 11 oz / 310 g walnut halves, toasted & cooled",
		"4 cups / 1 lb / 453 g confectioner's (powdered) sugar",
		"1/2 cup plus 3 tablespoons / 2 oz / 60 g unsweetened cocoa powder",
		"scant 1/2 teaspoon fine grain sea salt",
		"4 large egg whites, room temperature",
		"1 tablespoon real, good-quality vanilla extract" };
	private static final String[] STEPS = new String[] { 
		"Preheat oven to 320F / 160C degrees and position racks in the top and bottom third. Line three (preferably rimmed) baking sheets with parchment paper. Or you can bake in batches with fewer pans.",
		"Make sure your walnuts have cooled a bit, then chop coarsely and set aside. Sift together the confectioner's sugar, cocoa powder, and sea salt. Stir in the walnuts, then add the egg whites and vanilla. Stir until well combined.",
		"Bake until they puff up. The tops should get glossy, and then crack a bit - about 12 -15 minutes. Have faith, they look sad at first, then really blossom. You may want to rotate the pans top/bottom/back/front.",
		"Bake until they puff up. The tops should get glossy, and then crack a bit - about 12 -15 minutes. Have faith, they look sad at first, then really blossom. You may want to rotate the pans top/bottom/back/front." };


//	private final int wordBarColor = R.color.red;
//	private final int numberBarColor = R.color.green;

	public IngredientsStepsFragment(String tag) {
		mTag = tag;
		mTotalLength = TabsFragment.TAB_INGREDIENTS.equals(mTag) ? INGREDIENTS.length
				: STEPS.length;

		Log.d(TAG, "Constructor: tag=" + tag);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);
		mInflater = LayoutInflater.from(getActivity());

		// you only need to instantiate these the first time your fragment is
		// created; then, the method above will do the rest
		if (mAdapter == null) {
			mItems = new ArrayList<String>();
			mAdapter = new MyAdapter(getActivity(), mItems);
		}
		getListView().setAdapter(mAdapter);
		// initiate the loader to do the background work
		getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public Loader<Void> onCreateLoader(int id, Bundle args) {
		AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

			@Override
			public Void loadInBackground() {
//				try {
					//load images here if any
//					// simulate some time consuming operation going on in the
//					// background
//					//Thread.sleep(SLEEP);
//				} catch (InterruptedException e) {
//				}
				return null;
			}
		};
		// somehow the AsyncTaskLoader doesn't want to start its job without
		// calling this method
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Void> loader, Void result) {

		// add the new item and let the adapter know in order to refresh the
		// views
		mItems.add(TabsFragment.TAB_INGREDIENTS.equals(mTag) ? INGREDIENTS[mPosition]
				: STEPS[mPosition]);
		mAdapter.notifyDataSetChanged();

		// advance in your list with one step
		mPosition++;
		if (mPosition < mTotalLength) {
			getLoaderManager().restartLoader(0, null, this);
			Log.d(TAG, "onLoadFinished(): loading next...");
		} else {
			Log.d(TAG, "onLoadFinished(): done loading!");
		}
		
		setListShown(true);
		//changeHeight();
	}
	

	public void changeHeight(){
		final LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.instructions_layout);
		//int placeholder = TabsFragment.TAB_INGREDIENTS.equals(mTag) ? R.id.tab_1 :  R.id.tab_2;
		 RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
 		
		if(TabsFragment.TAB_INGREDIENTS.equals(mTag)){
			params.height = (int)(mTotalLength * 50);
		}
		else if(TabsFragment.TAB_STEPS.equals(mTag)){
			params.height = (int)(mTotalLength * 20);
		}
//		final FrameLayout fm = (FrameLayout) getActivity().findViewById(R.id.list_item_wrap);
//	    fm.post(new Runnable() {
//
//	        @Override
//	        public void run() {             
//	            int height = fm.getHeight();
//	            if(height == 0){
//	            	 
//	            }
//	           
//
//	    		Log.d(TAG, "length:" + String.valueOf(mTotalLength));
//	    		Log.d(TAG, "height:" + String.valueOf(height/2));
//	        }
//
//	    });
	}

	@Override
	public void onLoaderReset(Loader<Void> loader) {
	}

	private class MyAdapter extends ArrayAdapter<String> {
		
		public MyAdapter(Context context, List<String> objects) {
			super(context, R.layout.ingredients_list_item, R.id.text, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Wrapper wrapper;

			if (view == null) {
				view = mInflater.inflate(R.layout.ingredients_list_item, null);
				wrapper = new Wrapper(view);
				view.setTag(wrapper);

			} else {
				wrapper = (Wrapper) view.getTag();
			}
			Log.d(TAG, "LOADED ARRAYADAPTER");
			wrapper.getTextView().setText(getItem(position));
//			wrapper.getBar().setBackgroundColor(
//					mTag == TabsFragment.TAB_INGREDIENTS ? getResources().getColor(
//							wordBarColor) : getResources().getColor(
//							numberBarColor));
			return view;
		}

	}
	
	// use an wrapper (or view holder) object to limit calling the
	// findViewById() method, which parses the entire structure of your
	// XML in search for the ID of your view
	private class Wrapper {
		private final View mRoot;
		private TextView mText;
		private View mBar;
		private int height;
		public Wrapper(View root) {
			mRoot = root;
		}

		public TextView getTextView() {
			if (mText == null) {
				mText = (TextView) mRoot.findViewById(R.id.text);
			}
			return mText;
		}

//		public View getBar() {
//			if (mBar == null) {
//				mBar = mRoot.findViewById(R.id.bar);
//			}
//			return mBar;
//		}
	}
}