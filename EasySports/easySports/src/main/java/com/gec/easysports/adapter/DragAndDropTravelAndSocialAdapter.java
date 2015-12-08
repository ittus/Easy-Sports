package com.gec.easysports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gec.easysports.R;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ittus on 12/5/15.
 */
public class DragAndDropTravelAndSocialAdapter extends BaseAdapter implements
        Swappable {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DashboardModel> mDummyModelList;


    public DragAndDropTravelAndSocialAdapter(Context context,
                                             ArrayList<DashboardModel> dbModel) {
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDummyModelList = dbModel;

    }



    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mDummyModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDummyModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDummyModelList.get(position).getId();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo) {
        Collections.swap(mDummyModelList, positionOne, positionTwo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(
                    R.layout.list_item_drag_and_drop_travel, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.drag_and_drop_travel_image);
            holder.text = (TextView) convertView
                    .findViewById(R.id.drag_and_drop_travel_text);
            holder.subtext = (TextView) convertView
                    .findViewById(R.id.drag_and_drop_travel_subtext);
            holder.icon = (TextView) convertView
                    .findViewById(R.id.drag_and_drop_travel_icon);
            holder.rating = (TextView) convertView
                    .findViewById(R.id.drag_and_drop_travel_rating);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DashboardModel dm = mDummyModelList.get(position);


        ImageUtil.displayImage(holder.image, dm.getmImageURL(), null);
        holder.text.setText(dm.getName());
        holder.subtext.setText(dm.getAddress());
        holder.rating.setText(dm.getDate());

        holder.icon.setText(R.string.fontello_drag_and_drop);


        return convertView;
    }

    private static class ViewHolder {
        public ImageView image;
        public/* Roboto */TextView text;
        public/* Roboto */TextView subtext;
        public/* Fontello */TextView icon;
        public/* Roboto */TextView rating;
        public/* Roboto */TextView place;
    }
}
