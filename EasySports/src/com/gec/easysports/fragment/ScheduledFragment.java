package com.gec.easysports.fragment;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gec.easysports.R;
import com.gec.easysports.adapter.MyStickyListHeadersTravelAdapter;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;

public class ScheduledFragment extends Fragment implements OnItemClickListener {

	public static ScheduledFragment newInstance() {
		return new ScheduledFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_stickylistheaders,
				container, false);
		StickyListHeadersListView listView = (StickyListHeadersListView) rootView
				.findViewById(R.id.activity_stickylistheaders_listview);
		listView.setFitsSystemWindows(true);

		AlphaInAnimationAdapter animationAdapter;

		MyStickyListHeadersTravelAdapter adapterTravel = new MyStickyListHeadersTravelAdapter(getActivity().getApplicationContext());
		animationAdapter = new AlphaInAnimationAdapter(adapterTravel);

		StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(
				animationAdapter);
		stickyListHeadersAdapterDecorator
				.setListViewWrapper(new StickyListHeadersListViewWrapper(
						listView));
		assert animationAdapter.getViewAnimator() != null;
		animationAdapter.getViewAnimator().setInitialDelayMillis(500);
		assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
		stickyListHeadersAdapterDecorator.getViewAnimator()
				.setInitialDelayMillis(500);
		listView.setAdapter(stickyListHeadersAdapterDecorator);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

}
