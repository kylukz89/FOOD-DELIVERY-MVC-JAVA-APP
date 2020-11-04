package com.kylukz.fooddev.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;

import com.aide.aiDelivery.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapPontoActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView();
        setContentView(R.layout.activity_maps_ponto);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onResume() {
//        mMap.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
//        mMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
//        mMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
//        mMap.onLowMemory();
        super.onLowMemory();
    }

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    private Marker myMarker;





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


        ///////////////////////////////////////////////////////////////////////////////////////////
        // EU - Meu Local
        LatLng eu = new LatLng(-20.9060696, -51.3949802);
        Marker melbourne20 = mMap.addMarker(new MarkerOptions().position(eu).title("Eu"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eu));
        melbourne20.showInfoWindow();

        // Local Restaurante
        LatLng festa8 = new LatLng(-20.9215343, -51.3747127);
        Marker melbourne8 = mMap.addMarker(new MarkerOptions().position(festa8).title("Restaurante Sabon Caseiro").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(festa8));
        melbourne8.showInfoWindow();


        mMap.setOnMarkerClickListener(this);

        // ZOOM ADEQUADO
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.5f));


        //////////////////////////////////////////
        // PARA CENTRALIZAR COM TODAS AS MARCAS //
        //////////////////////////////////////////
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // Laço para o mpaa se ajustar entre todas as marcas
        builder.include(melbourne20.getPosition());

        mMap.setOnMarkerClickListener(this);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intentRestaurante = new Intent(MapPontoActivity.this, EmpresaActivity.class);
                startActivity(intentRestaurante);
                return true;
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        return false;
    }
}
