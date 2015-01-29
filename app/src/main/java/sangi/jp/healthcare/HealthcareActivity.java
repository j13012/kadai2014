package sangi.jp.healthcare;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

import static android.app.AlertDialog.Builder;

public class HealthcareActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback, LocationListener, GooglePlayServicesClient.OnConnectionFailedListener, GooglePlayServicesClient.ConnectionCallbacks {
    public double lat = 0;
    public double lon = 0;
    private GoogleMap mMap;
    Button btn1,btn2, btn3;
    TextView textView1, textView2;
    LatLng latlng;
    Toast toast = Toast.makeText(this, "追加しました", Toast.LENGTH_LONG);
    static SQLiteDatabase healthcare_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthcare);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        btn1 = (Button)findViewById(R.id.b1);
        btn2 = (Button)findViewById(R.id.b2);
        btn3 = (Button)findViewById(R.id.b3);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            mapFragment.setRetainInstance(true);
        } else {
            mMap = mapFragment.getMap();
        }
        setUpMapIfNeeded();
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 20000, 0, this);



        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthcareActivity.this, PedometerActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HealthcareActivity.this, HealthcareDb.class);
                startActivity(intent2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg;
                dlg = new AlertDialog.Builder(HealthcareActivity.this);
                dlg.setMessage("この地点を登録しますか？");
                dlg.setCancelable(false);
                dlg.setPositiveButton("はい", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add();
                        dialog.cancel();
                        toast.show();
                    }
                });
                dlg.setPositiveButton("いいえ", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dlg.show();

            }
        });
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        textView1.setText("緯度：" + lat);
        textView2.setText("経度：" + lon);
        latlng = new LatLng(lat, lon);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    private void add(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String created = Calendar.getInstance().getTime().toString();
        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lon);
        ContentValues values = new ContentValues();
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        long rowID = db.insert("history", "", values);
        if(rowID == -1){
            db.close();
            throw new SQLException("Failed to insert row");
    }db.close();
}
}
