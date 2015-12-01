package com.gec.easysports;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gec.easysports.adapter.DrawerAdapter;
import com.gec.easysports.fragment.DashboardFragment;
import com.gec.easysports.fragment.ScheduledFragment;
import com.gec.easysports.fragment.TextViewsFragment;
import com.gec.easysports.fragment.TopicsFragment;
import com.gec.easysports.model.DrawerItem;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements
		OnDismissCallback {

	private ListView mDrawerList;
	private List<DrawerItem> mDrawerItems;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private Handler mHandler;

	private boolean mShouldFinish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageLoader imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited()) {
			imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		prepareNavigationDrawerItems();
		mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mHandler = new Handler();

		if (savedInstanceState == null) {
			int position = 0;
			selectItem(position, mDrawerItems.get(position).getTag());
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

	@Override
	public void onBackPressed() {
		if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
			Toast.makeText(getApplicationContext(), R.string.confirm_exit,
					Toast.LENGTH_SHORT).show();
			mShouldFinish = true;
			mDrawerLayout.openDrawer(mDrawerList);
		} else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			super.onBackPressed();
		}
	}

	private void prepareNavigationDrawerItems() {
		mDrawerItems = new ArrayList<>();
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_left_menus,
				R.string.drawer_title_dashboard,
				DrawerItem.DRAWER_ITEM_TAG_DASHBOARD));

		mDrawerItems
				.add(new DrawerItem(R.string.drawer_icon_parallax,
						R.string.drawer_title_topics,
						DrawerItem.DRAWER_ITEM_TAG_TOPICS));

		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_login_page,
				R.string.drawer_title_scheduled,
				DrawerItem.DRAWER_ITEM_TAG_SCHEDULED));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_list_views,
				R.string.drawer_title_profile,
				DrawerItem.DRAWER_ITEM_TAG_PROFILE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, mDrawerItems.get(position).getTag());
		}
	}

	private void selectItem(int position, int drawerTag) {
		Fragment fragment = getFragmentByDrawerTag(drawerTag);
		commitFragment(fragment);

		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerItems.get(position).getTitle());
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private Fragment getFragmentByDrawerTag(int drawerTag) {
		Fragment fragment;
		if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_DASHBOARD) {
			fragment = DashboardFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SCHEDULED) {
			fragment = ScheduledFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_TOPICS) {
			fragment = TopicsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_PROFILE) {
			fragment = TextViewsFragment.newInstance();
		} else {
			fragment = new Fragment();
		}
		mShouldFinish = false;
		return fragment;
	}

	private class CommitFragmentRunnable implements Runnable {

		private Fragment fragment;

		public CommitFragmentRunnable(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void run() {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}
	}

	public void commitFragment(Fragment fragment) {
		// Using Handler class to avoid lagging while
		// committing fragment in same time as closing
		// navigation drawer
		mHandler.post(new CommitFragmentRunnable(fragment));
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onDismiss(@NonNull ViewGroup arg0, @NonNull int[] arg1) {
		// TODO Auto-generated method stub

	}
}