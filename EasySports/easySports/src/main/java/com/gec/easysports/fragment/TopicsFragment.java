package com.gec.easysports.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.gec.easysports.R;
import com.gec.easysports.adapter.ImageGalleryCategoryAdapter;
import com.gec.easysports.util.DummyContent;
import com.parse.ParseInstallation;

import java.util.List;

public class TopicsFragment extends Fragment implements OnItemClickListener {
	private ListView mListView;

	public static TopicsFragment newInstance() {
		return new TopicsFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_view, container, false);
		mListView = (ListView) rootView.findViewById(R.id.list_view);

		List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
		BaseAdapter adapter = new ImageGalleryCategoryAdapter(getActivity(),
				DummyContent.getImageGalleryMusicCategories(subscribedChannels), true);

		mListView.setAdapter(adapter);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

}