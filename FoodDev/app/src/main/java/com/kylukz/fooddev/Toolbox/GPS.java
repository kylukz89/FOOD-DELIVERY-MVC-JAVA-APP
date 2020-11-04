package com.kylukz.fooddev.Toolbox;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import java.util.List;

public class GPS extends Activity implements LocationListener {
    private LocationManager mgr;
    private String best;
    public static double myLocationLatitude;
    public static double myLocationLongitude;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        dumpProviders();
        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mgr.getLastKnownLocation(best);
        dumpLocation(location);
    }

    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        dumpLocation(location);

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onPause() {

        super.onPause();
        mgr.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {

        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mgr.requestLocationUpdates(best, 15000, 1, this);
    }

    private void dumpLocation(Location l) {

        if (l == null) {

            myLocationLatitude = 0.0;
            myLocationLongitude = 0.0;
        } else {

            myLocationLatitude = l.getLatitude();
            myLocationLongitude = l.getLongitude();
        }
    }

    private void dumpProviders() {

        List<String> providers = mgr.getAllProviders();
        for (String p : providers) {

            dumpProviders(p);
        }
    }

    private void dumpProviders(String s) {

        LocationProvider info = mgr.getProvider(s);
        StringBuilder builder = new StringBuilder();
        builder.append("name: ").append(info.getName());
    }

}