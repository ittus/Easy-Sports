package com.gec.easysports.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gec.easysports.R;

public class TextViewsFragment extends Fragment {

	public static TextViewsFragment newInstance() {
		return new TextViewsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_text_views, container, false);
		
		return rootView;
	}
}
