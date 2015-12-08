package com.gec.easysports.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.gec.easysports.R;
import com.gec.easysports.adapter.DragAndDropTravelAndSocialAdapter;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.NetworkUtils;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ScheduledFragment extends Fragment implements OnItemClickListener {
	public static final String DRAG_AND_DROP_TRAVEL = "drag.and.drop.travel";
	private DynamicListView mDynamicListView;
	private ProgressDialog pDialog;
	 ArrayList<DashboardModel> listDashboard;
	public  ScheduledFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listDashboard = new ArrayList<DashboardModel>();
		if (NetworkUtils.isNetworkConnectionAvailable(getActivity())) {
			getData();
		}else{
			Toast.makeText(getActivity(), "Network connection error!!!!", Toast.LENGTH_LONG).show();
		}


	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_list_views,
				container, false);

		mDynamicListView = (DynamicListView) rootView.findViewById(R.id.dynamic_listview);
		mDynamicListView.setDividerHeight(0);


		return rootView;
	}
	private void setUpDragAndDrop() {
		final DragAndDropTravelAndSocialAdapter adapter = new DragAndDropTravelAndSocialAdapter(getActivity().getApplicationContext(), listDashboard);
		mDynamicListView.setAdapter(adapter);
		mDynamicListView.enableDragAndDrop();
		TouchViewDraggableManager tvdm = new TouchViewDraggableManager(
				R.id.drag_and_drop_travel_icon);
		mDynamicListView.setDraggableManager(new TouchViewDraggableManager(
				R.id.icon));
		mDynamicListView.setDraggableManager(tvdm);
		mDynamicListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
												   View view, int position, long id) {
						mDynamicListView.startDragging(position);
						return true;
					}
				});
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	private void getData(){
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();

		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Register");
		query.whereEqualTo("user", user);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				for (int i = 0; i < objects.size(); i++) {
					ParseObject parseObj = objects.get(i);
					DashboardModel dbModel = new DashboardModel();
					dbModel.setName(parseObj.getString(DashboardModel.TAG_NAME));
					dbModel.setmImageURL(parseObj.getString(DashboardModel.TAG_IMAGE_URL));
					dbModel.setDate(parseObj.getString(DashboardModel.TAG_DATE));
					dbModel.setAddress(parseObj.getString(DashboardModel.TAG_ADDRESS));
					dbModel.setTime(parseObj.getString(DashboardModel.TAG_TIME));

					//TODO: fill in all fields of Dbmodels

					listDashboard.add(dbModel);
				}
				setUpDragAndDrop();
				if (pDialog.isShowing())
					pDialog.dismiss();
			}
		});

	}

}
