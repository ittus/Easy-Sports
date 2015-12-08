package com.gec.easysports.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gec.easysports.R;
import com.gec.easysports.adapter.GoogleCardsTravelAdapter;
import com.gec.easysports.constants.Constants;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.services.ServiceHandler;
import com.gec.easysports.util.NetworkUtils;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements OnItemClickListener {


    private static final int INITIAL_DELAY_MILLIS = 300;



    private GoogleCardsTravelAdapter mGoogleCardsAdapter;
    private ListView listView;
    private ProgressDialog pDialog;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(NetworkUtils.isNetworkConnectionAvailable(getActivity().getApplicationContext())) {
            new GetDashboard().execute();
        }else{
            Toast.makeText(getActivity(), "Network connection error!!!!", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view,
                container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);


        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_create:
                Toast.makeText(getActivity(), "Create new events", Toast.LENGTH_SHORT).show();
                //change to create fragment
                Fragment fragment = CreateNewEventFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

     class GetDashboard extends AsyncTask<Void, Void, ArrayList<DashboardModel>> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(getActivity());
             pDialog.setMessage("Please wait...");
             pDialog.setCancelable(false);
             pDialog.show();
         }

         @Override
        protected ArrayList<DashboardModel> doInBackground(Void... voids) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(Constants.URL_GET_DATA, ServiceHandler.GET);

            Log.d("GET_DASHBOARD_TASK", jsonStr);

            ArrayList<DashboardModel> list = new ArrayList<>();
            //parse
            if(jsonStr != null){
                try{
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for(int i = 0;i < jsonArray.length(); i++){
                        JSONObject dashboardJSON = jsonArray.getJSONObject(i);

                        String name = dashboardJSON.getString(DashboardModel.TAG_NAME);
                        String description = dashboardJSON.getString(DashboardModel.TAG_DESCRIPTION);
                        String category = dashboardJSON.getString(DashboardModel.TAG_CATEGORY);
                        String url = dashboardJSON.getString(DashboardModel.TAG_IMAGE_URL);
                        String date = dashboardJSON.getString(DashboardModel.TAG_DATE);
                        String time = dashboardJSON.getString(DashboardModel.TAG_TIME);
                        String address = dashboardJSON.getString(DashboardModel.TAG_ADDRESS);
                        String location = dashboardJSON.getString(DashboardModel.TAG_LOCATION);
                        String numPlayer = dashboardJSON.getString(DashboardModel.TAG_NUM_OF_PLAYER);
                        String userEmail = dashboardJSON.getString(DashboardModel.TAG_USER_EMAIL);
//                        String locations = dashboardJSON.getJSONArray(DashboardModel.TAG_LOCATION);

                        DashboardModel dbModel = new DashboardModel();
                        dbModel.setName(name);
                        dbModel.setmDescription(description);
                        dbModel.setCategory(category);
                        dbModel.setmImageURL(url);
                        dbModel.setDate(date);
                        dbModel.setTime(time);
                        dbModel.setAddress(address);
                        dbModel.setLocation(location);
                        dbModel.setNumPlayer(numPlayer);
                        dbModel.setUserEmail(userEmail);

                        list.add(dbModel);
                    }
                }catch (JSONException ex){
                    Log.d("Exception ittus",ex.getMessage());
                }
            }
            return list;
        }

         @Override
         protected void onPostExecute(ArrayList<DashboardModel> dashboardModels) {
             if (pDialog.isShowing())
                 pDialog.dismiss();
             mGoogleCardsAdapter = new GoogleCardsTravelAdapter(getActivity(), dashboardModels, getActivity().getSupportFragmentManager());
             SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                     new SwipeDismissAdapter(mGoogleCardsAdapter, (OnDismissCallback) getActivity()));
             swingBottomInAnimationAdapter.setAbsListView(listView);
             assert swingBottomInAnimationAdapter.getViewAnimator() != null;
             swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                     INITIAL_DELAY_MILLIS);

             listView.setClipToPadding(false);
             listView.setDivider(null);
             Resources r = getResources();
             int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                     8, r.getDisplayMetrics());
             listView.setDividerHeight(px);
             listView.setFadingEdgeLength(0);
             listView.setFitsSystemWindows(true);
             px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                     r.getDisplayMetrics());
             listView.setPadding(px, px, px, px);
             listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
             listView.setAdapter(swingBottomInAnimationAdapter);
             listView.setOnItemClickListener(DashboardFragment.this);

             super.onPostExecute(dashboardModels);
         }
     }


}
