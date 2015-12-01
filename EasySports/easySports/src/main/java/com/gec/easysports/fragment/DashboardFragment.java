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
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements OnItemClickListener {


    private static final int INITIAL_DELAY_MILLIS = 300;

    private static final String TAG_NAME = "name";
    private static final String TAG_GAME = "game";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LOCATION = "location";

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
        new GetDashboard().execute();

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
                        .replace(R.id.content_frame, fragment).commit();
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

                        String name = dashboardJSON.getString(TAG_NAME);
                        String description = dashboardJSON.getString(TAG_DESCRIPTION);
                        String game = dashboardJSON.getString(TAG_GAME);
                        String url = "http://kenh14.vcmedia.vn/zoom/421_263/534992cb49/2015/10/15/avah-bf3f9.jpg";
                        JSONArray locations = dashboardJSON.getJSONArray(TAG_LOCATION);

                        DashboardModel dbModel = new DashboardModel(i, url, game, description);
                        list.add(dbModel);
                    }
                }catch (JSONException ex){

                }
            }
            return list;
        }

         @Override
         protected void onPostExecute(ArrayList<DashboardModel> dashboardModels) {
             if (pDialog.isShowing())
                 pDialog.dismiss();
             mGoogleCardsAdapter = new GoogleCardsTravelAdapter(getActivity(),
                     dashboardModels);
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
