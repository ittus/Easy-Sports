package com.gec.easysports;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.ImageUtil;
import com.gec.easysports.util.NetworkUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ittus on 12/5/15.
 */
public class DetailActivity extends Activity {
    ImageView ivGame;
    TextView rtvDescription;
    TextView tvDateView, tvTimeView, tvLocation, tvNumPlayer;
    TextView tvName, tvCategory;
    private DashboardModel dbModel;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

        ivGame = (ImageView) findViewById(R.id.ivGame);
        rtvDescription = (TextView) findViewById(R.id.rtvDescription);
        tvDateView = (TextView) findViewById(R.id.tvDateView);
        tvTimeView = (TextView) findViewById(R.id.tvTimeView);
        tvLocation = (TextView) findViewById(R.id.tvLocationView);
        tvName = (TextView) findViewById(R.id.tvNameView);
        tvCategory = (TextView) findViewById(R.id.tvCategoryView);
        tvNumPlayer = (TextView) findViewById(R.id.tvNumPlayer);

        dbModel = new DashboardModel();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String date = extras.getString(DashboardModel.TAG_DATE);
        if (date != null) {
            tvDateView.setText(date);
            dbModel.setDate(date);
        }
        String time = extras.getString(DashboardModel.TAG_TIME);
        if (time != null) {
            tvTimeView.setText(time);
            dbModel.setTime(time);
        }

        String address = extras.getString(DashboardModel.TAG_ADDRESS);
        tvLocation.setText(address);
        dbModel.setAddress(address);

        String name = extras.getString(DashboardModel.TAG_NAME);
        tvName.setText(name);
        dbModel.setName(name);

        String category = extras.getString(DashboardModel.TAG_CATEGORY);
        tvCategory.setText(category);
        dbModel.setCategory(category);

        String imageUrl = extras.getString(DashboardModel.TAG_IMAGE_URL);
        Log.d("Detail activity", imageUrl);
        ImageUtil.displayImage(ivGame, imageUrl, null);
        dbModel.setmImageURL(imageUrl);

        String description = extras.getString(DashboardModel.TAG_DESCRIPTION);
        rtvDescription.setText(description);
        dbModel.setmDescription(description);

        String numberPlayer = extras.getString(DashboardModel.TAG_NUM_OF_PLAYER);
        tvNumPlayer.setText(numberPlayer);
        dbModel.setNumPlayer(numberPlayer);

        final String location = extras.getString(DashboardModel.TAG_LOCATION);
        dbModel.setLocation(location);
        Log.d("Detail activity locati", location);
        if (location != null) {
            final String[] locArrays = location.split(",");

            final Button btnView = (Button) findViewById(R.id.btnViewOnMap);
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    i.putExtra(MapActivity.EXTRA_LOCATION, location);
                    startActivity(i);
                }
            });
        }

        TextView tvRegister = (TextView) findViewById(R.id.rtvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Do you want to register event " + dbModel.getName() + " at " +
                        dbModel.getAddress());
                builder.setTitle("Confirm registration");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if (NetworkUtils.isNetworkConnectionAvailable(getApplicationContext())) {
                            saveToCloud(dbModel);
                        } else {
                            Toast.makeText(DetailActivity.this, "Network is not available!!!!", Toast.LENGTH_LONG).show();
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

        TextView tvReport = (TextView) findViewById(R.id.rtvReport);
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Do you want to report this event?");
                builder.setTitle("Report");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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

        ImageView profileImage = (ImageView)
                findViewById(R.id.list_item_google_cards_social_profile_image);
        ImageUtil.displayRoundImage(profileImage, "http://anh.eva.vn/upload/4-2014/images/2014-11-26/1416987533-lekhanh-eva7.jpg",
                null);
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
        pDialog = new ProgressDialog(this);
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
                    Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Register successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        String imageUrl = intent.getStringExtra(DashboardModel.TAG_IMAGE_URL);
//        String description = intent.getStringExtra(DashboardModel.TAG_DESCRIPTION);
//        String date = intent.getStringExtra(DashboardModel.TAG_DATE);
//        String time = intent.getStringExtra(DashboardModel.TAG_TIME);
//
////        ImageUtil.displayImage(ivGame, imageUrl, null);
//        rtvDescription.setText(description);
//        tvDateView.setText("vu minh thang");
//        tvTimeView.setText(time);
    }
}
