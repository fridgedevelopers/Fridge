package com.fridge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabsFragment extends Fragment implements OnTabChangeListener {

	private static final String TAG = "FragmentTabs";
	public static final String TAB_INGREDIENTS = "ingredients";
	public static final String TAB_STEPS = "steps";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

	public TabsFragment(){}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.tabs_fragment, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		updateTab(TAB_INGREDIENTS, R.id.tab_1);

	}

	private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_INGREDIENTS, R.string.tab_ingredients, R.id.tab_1));
		mTabHost.addTab(newTab(TAB_STEPS, R.string.tab_steps, R.id.tab_2));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		Log.d(TAG, "buildTab(): tag=" + tag);

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab,
				(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.text)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_INGREDIENTS.equals(tabId)) {
			updateTab(tabId, R.id.tab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_STEPS.equals(tabId)) {
			updateTab(tabId, R.id.tab_2);
			mCurrentTab = 1;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();
		IngredientsStepsFragment f = new IngredientsStepsFragment(tabId);
		if (fm.findFragmentByTag(tabId) == null) {
			fm.beginTransaction()
					.replace(placeholder, f, tabId)
					.commit();
			
		}
	}

}
