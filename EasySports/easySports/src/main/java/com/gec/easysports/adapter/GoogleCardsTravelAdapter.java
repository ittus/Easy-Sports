package com.gec.easysports.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gec.easysports.R;
import com.gec.easysports.fragment.DetailTopicFragment;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.ImageUtil;

import java.util.List;

public class GoogleCardsTravelAdapter extends ArrayAdapter<DashboardModel>
		implements OnClickListener {

	private LayoutInflater mInflater;
	Context context;
	FragmentManager fmManager;
	private DashboardModel item;
	public GoogleCardsTravelAdapter(FragmentActivity context, List<DashboardModel> items,FragmentManager fmManager) {
		super(context, 0, items);
		this.context = context;
		this.fmManager = fmManager;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}



	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_travel, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_travel_image);
			holder.categoryName = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_category_name);
			holder.title = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_title);
			holder.text = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_text);
			holder.explore = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_explore);
			holder.share = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_share);
			holder.explore.setOnClickListener(this);
			holder.share.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		item = getItem(position);
		ImageUtil.displayImage(holder.image, item.getmImageURL(), null);
		holder.title.setText(item.getName());
		holder.text.setText(item.getmDescription());
		holder.explore.setTag(position);
		holder.categoryName.setText(item.getCategory());
		holder.share.setTag(position);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView categoryName;
		public TextView title;
		public TextView text;
		public TextView explore;
		public TextView share;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.list_item_google_cards_travel_explore:
			Fragment fragment = new DetailTopicFragment(item, context);
			fmManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			break;
		case R.id.list_item_google_cards_travel_share:
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Please join activity " + item.getCategory() + " with us at " + item.getAddress();
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		}
	}
}
