package com.gec.easysports.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gec.easysports.R;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.ImageUtil;
import com.gec.easysports.util.NetworkUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ittus on 12/5/15.
 */
public class DetailTopicFragment extends Fragment {

    private DashboardModel dbModel;
    private Context context;
    private ProgressDialog pDialog;

    public DetailTopicFragment(DashboardModel dbModel, Context context) {
        this.dbModel = dbModel;
        this.context = context;

    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ImageView ivGame = (ImageView) rootView.findViewById(R.id.ivGame);
        ImageUtil.displayImage(ivGame, dbModel.getmImageURL(), null);

        TextView rtvDescription = (TextView) rootView.findViewById(R.id.rtvDescription);
        rtvDescription.setText(dbModel.getmDescription());

        TextView tvDateView = (TextView) rootView.findViewById(R.id.tvDateView);
        tvDateView.setText(dbModel.getDate());

        TextView tvTimeView = (TextView) rootView.findViewById(R.id.tvTimeView);
        tvTimeView.setText(dbModel.getTime());

        TextView tvLocationView = (TextView) rootView.findViewById(R.id.tvLocationView);
        tvLocationView.setText(dbModel.getAddress());

        TextView tvCategoryView = (TextView) rootView.findViewById(R.id.tvCategoryView);
        tvCategoryView.setText(dbModel.getCategory());

        TextView tvName = (TextView) rootView.findViewById(R.id.tvNameView);
        tvName.setText(dbModel.getName());

        TextView tvNumberOfPlayer = (TextView) rootView.findViewById(R.id.tvNumPlayer);
        tvNumberOfPlayer.setText(dbModel.getNumPlayer());

        TextView tvRegister = (TextView) rootView.findViewById(R.id.rtvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to register event " + dbModel.getName() + " at " + dbModel.getAddress());
                builder.setTitle("Confirm registration");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if (NetworkUtils.isNetworkConnectionAvailable(context)) {
                            saveToCloud(dbModel);
                        } else {
                            Toast.makeText(context, "Network connection error!!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button btnLocation = (Button) rootView.findViewById(R.id.btnViewOnMap);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 0;
                double longtitude = 0;
                String location = dbModel.getLocation();
                String[] intArrs = location.split(",");
                latitude = Double.parseDouble(intArrs[0]);
                longtitude = Double.parseDouble(intArrs[1]);
                CustomMapFragment fragment = new CustomMapFragment(latitude, longtitude);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack("Details").commit();
            }
        });

        ImageView profileImage = (ImageView) rootView
                .findViewById(R.id.list_item_google_cards_social_profile_image);
        ImageUtil.displayRoundImage(profileImage, "http://anh.eva.vn/upload/4-2014/images/2014-11-26/1416987533-lekhanh-eva7.jpg",
                null);
        return rootView;
    }

    private void saveToCloud(DashboardModel dbModel) {
        ParseUser user = ParseUser.getCurrentUser();

// Make a new post
        ParseObject post = new ParseObject("Register");
        post.put(DashboardModel.TAG_NAME, dbModel.getName());
        post.put(DashboardModel.TAG_CATEGORY, dbModel.getCategory());
        post.put(DashboardModel.TAG_ADDRESS, dbModel.getAddress());
        post.put(DashboardModel.TAG_DESCRIPTION, dbModel.getmDescription());
        post.put(DashboardModel.TAG_TIME, dbModel.getTime());
        post.put(DashboardModel.TAG_DATE, dbModel.getDate());
        post.put(DashboardModel.TAG_LOCATION, dbModel.getLocation());
        post.put(DashboardModel.TAG_IMAGE_URL, dbModel.getmImageURL());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        post.put("user", user);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();
                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Register successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
