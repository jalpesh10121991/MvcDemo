package in.mvcdemo.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.R;
import in.mvcdemo.Utills.VPreferences;

public class LocateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CustomTextView txt_title;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_title = (CustomTextView) findViewById(R.id.txt_title);


        toolbar.setTitle("Locate Events");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        toolbar.setNavigationIcon(R.drawable.ic_back_new);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(VPreferences.getPreferanceLattitude(LocateActivity.this)), Double.parseDouble(VPreferences.getPreferanceLongitude(LocateActivity.this)));
        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Event Located"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
