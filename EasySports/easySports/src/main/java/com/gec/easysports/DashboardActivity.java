package com.gec.easysports;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gec.easysports.adapter.SubcategoryAdapter;

public class DashboardActivity extends ActionBarActivity implements
		OnItemClickListener {

	public static final String LIST_VIEW_CATEGORY_EXPANDABLES = "com.gec.easysports.Expandables";
	public static final String LIST_VIEW_CATEGORY_DRAG_AND_DROP = "com.gec.easysports.DragAndDrop";
	public static final String LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS = "com.gec.easysports.SwipeToDissmiss";
	public static final String LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS = "com.gec.easysports.AppearanceAnimations";
	public static final String LIST_VIEW_CATEGORY_STICKY_HEADER = "com.gec.easysports.StickyHeader";
	public static final String LIST_VIEW_CATEGORY_GOOGLE_CARDS = "com.gec.easysports.GoogleCards";

	public static final String APPEARANCE_ANIMATIONS_LIST_VIEW = "Appearance animations";

	public static final String LIST_VIEW_TRAVEL = "Travel";
	public static final String LIST_VIEW_SOCIAL = "Social";
	public static final String LIST_VIEW_UNIVERSAL = "Universal";

	public static final String LIST_VIEW_OPTION_EXPANDABLE = "Expandable";
	public static final String LIST_VIEW_OPTION_DRAG_AND_DROP = "Drag&Drop";
	public static final String LIST_VIEW_OPTION_SWIPE_TO_DISSMISS = "Swipe-to-dissmiss";
	public static final String LIST_VIEW_OPTION_APPERANCE_ALPHA = "Appearance animation (alpha)";
	public static final String LIST_VIEW_OPTION_APPERANCE_RANDOM = "Appearance animation (random)";
	public static final String LIST_VIEW_OPTION_STICKI_LIST_HEADERS = "Sticky list headers"; // Coming
																								// soon
	public static final String LIST_VIEW_OPTION_GOOGLE_CARDS = "Google Cards";
	public static final String LIST_VIEW_OPTION_APPEARANCE_ANIMIATIONS = "Appearance animations";

	private ListView mListView;
	private String listViewCategory;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		mListView = (ListView) findViewById(R.id.list_view);
		// String category = GOOGLE_CARDS_LIST_VIEW;
		String category = "";
		Bundle extras = getIntent().getExtras();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			category = extras.getString(LIST_VIEW_CATEGORY_GOOGLE_CARDS,
					LIST_VIEW_UNIVERSAL);
		} else {
			category = extras.getString(LIST_VIEW_CATEGORY_GOOGLE_CARDS);
		}

		setUpListView();

		listViewCategory = category;

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(category);
	}

	private void setUpListView() {
		List<String> subcategories = new ArrayList<>();
		subcategories.add(LIST_VIEW_TRAVEL);
		subcategories.add(LIST_VIEW_SOCIAL);
		subcategories.add(LIST_VIEW_UNIVERSAL);
		mListView.setAdapter(new SubcategoryAdapter(this, subcategories));
		mListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}