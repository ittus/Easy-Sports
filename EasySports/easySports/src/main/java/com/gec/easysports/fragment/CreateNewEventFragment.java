package com.gec.easysports.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gec.easysports.R;
import com.gec.easysports.constants.Constants;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.services.ServiceHandler;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by ittus on 11/29/15.
 */
public class CreateNewEventFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public static int PLACE_PICKER_REQUEST = 1;
    private TextView tvDate, tvTime, tvLocation, tvName, tvNumOfPlayers;
    private EditText etDescription, etName, etPlayers;
    private String longlatLocation = "";
    private String cateogory, address;
    private ProgressDialog pDialog;
    private LinearLayout llSuggestion;

    public static CreateNewEventFragment newInstance() {
        return new CreateNewEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_new_event,
                container, false);

        llSuggestion = (LinearLayout) rootView.findViewById(R.id.llSuggestion);
        final Spinner spSuggestion = (Spinner) rootView.findViewById(R.id.spinLocationSuggestion);
        //set data for spinner
        final Spinner spCategory = (Spinner) rootView.findViewById(R.id.spinCateogory);


        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.category_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(staticAdapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                cateogory = getResources().getStringArray(R.array.category_array)[pos];
                if (longlatLocation.length() > 0) {
                    //random value for sponner
                    int[] suggest = {R.array.stadium_array_1, R.array.stadium_array_2, R.array.stadium_array_3, R.array.stadium_array_4};
                    Random r = new Random();
                    int index = r.nextInt(4);
                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                            .createFromResource(getActivity(), suggest[index],
                                    android.R.layout.simple_spinner_item);

                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSuggestion.setAdapter(staticAdapter);
                    llSuggestion.setVisibility(View.VISIBLE);
                } else {
                    llSuggestion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cateogory = "Badminton";
            }
        });

        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvTime = (TextView) rootView.findViewById(R.id.tvTime);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etPlayers = (EditText) rootView.findViewById(R.id.etNumberPeople);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateNewEventFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);

                dpd.setAccentColor(Color.parseColor("#9C27B0"));
                dpd.setTitle("Set Date");

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        CreateNewEventFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true //mode 24 hours
                );
                tpd.setThemeDark(true);
                tpd.vibrate(true);
                tpd.dismissOnPause(true);
                tpd.enableSeconds(true);
                tpd.setAccentColor(Color.parseColor("#9C27B0"));
                tpd.setTitle("Set Time");

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
            }
        });

        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity().getApplicationContext()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        Button btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DashboardModel dbModal = new DashboardModel();
                dbModal.setLocation(longlatLocation);
                dbModal.setmImageURL("https://www.redsports.sg/wp-content/uploads/2013/06/sgu15-vs-corinthians.jpg");
                dbModal.setTime(tvTime.getText() + "");
                dbModal.setDate(tvDate.getText() + "");
                dbModal.setmDescription(etDescription.getText() + "");
                dbModal.setName(etName.getText() + "");
                dbModal.setNumPlayer(etPlayers.getText() + "");
                dbModal.setCategory(cateogory);
                dbModal.setAddress(address);
                dbModal.setUserEmail("thangvm@gmail.com");


                new CreateNewTask(dbModal).execute();

            }
        });

        TextView rtViewSuggestion = (TextView) rootView.findViewById(R.id.rtvViewSuggest);
        TextView rtUseIt = (TextView) rootView.findViewById(R.id.rtvUseIt);
        rtViewSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "This function is comming soon.", Toast.LENGTH_LONG).show();
            }
        });
        rtUseIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "This function is comming soon.", Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            Place place = PlacePicker.getPlace(data, getActivity());
//            String toastMsg = String.format("Position: %d %d", place.getLatLng().latitude, place.getLatLng().longitude);
//            Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            tvLocation.setText(place.getName());
            longlatLocation = place.getLatLng().latitude + "," + place.getLatLng().longitude;
            address = (String) place.getName();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        tvDate.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        tvTime.setText(time);
    }

    class CreateNewTask extends AsyncTask<Void, Void, String> {
        private DashboardModel dbModel;

        public CreateNewTask(DashboardModel dbModel) {
            this.dbModel = dbModel;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (str.contains("true")) {
                Toast.makeText(getActivity(), "Your event is created successfully", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Some errors happen", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jobj = new JSONObject();
            try {
                jobj.put(DashboardModel.TAG_NAME, dbModel.getName());
                jobj.put(DashboardModel.TAG_DESCRIPTION, dbModel.getmDescription());
                jobj.put(DashboardModel.TAG_CATEGORY, dbModel.getCategory());
                jobj.put(DashboardModel.TAG_LOCATION, dbModel.getLocation());
                jobj.put(DashboardModel.TAG_USER_EMAIL, dbModel.getUserEmail());
                jobj.put(DashboardModel.TAG_NUM_OF_PLAYER, dbModel.getNumPlayer());
                jobj.put(DashboardModel.TAG_IMAGE_URL, dbModel.getmImageURL());
                jobj.put(DashboardModel.TAG_TIME, dbModel.getTime());
                jobj.put(DashboardModel.TAG_DATE, dbModel.getDate());
                jobj.put(DashboardModel.TAG_CATEGORY, dbModel.getCategory());
                jobj.put(DashboardModel.TAG_ADDRESS, dbModel.getAddress());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            Log.d("CREATE_TASK", jobj.toString());
            String response = sh.sendJsonPostRequest(Constants.URL_CREATE_TASK, jobj);
            Log.d("CREATE_NEW_TASK", response);
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


    }

}
