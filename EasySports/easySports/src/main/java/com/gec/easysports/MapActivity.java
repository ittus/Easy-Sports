package com.gec.easysports;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ittus on 12/5/15.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    double longtitude = 0;
    double latitude = 0;
    public static String EXTRA_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            try {
                value = extras.getString(EXTRA_LOCATION);
                String[] valuesArray = value.split(",");
                latitude = Double.parseDouble(valuesArray[0]);
                longtitude = Double.parseDouble(valuesArray[1]);
            }catch (Exception ex){
                Toast.makeText(MapActivity.this,"Some errors happens", Toast.LENGTH_LONG).show();
                Log.d("CREATE_ERROR", ex.getMessage());
            }
        }else{
            Toast.makeText(MapActivity.this,"Some errors happens", Toast.LENGTH_LONG).show();
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(latitude, longtitude);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

        googleMap.addMarker(new MarkerOptions()
                .title("Position")
                .snippet("Position to play")
                .position(position));
    }
}
