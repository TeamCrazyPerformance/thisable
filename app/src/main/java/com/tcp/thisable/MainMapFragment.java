package com.tcp.thisable;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainMapFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;

    ArrayList<Data> listarray = new ArrayList<>();
    GoogleMap gMap;

    public MainMapFragment() {
    }

    public void updateUi(ArrayList<Data> d) {
        gMap.clear();

        listarray = d;

        for(final Data data: listarray) {
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(data.location.coordinates[1], data.location.coordinates[0])).title(data.name);
            gMap.addMarker(markerOptions);
            gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getActivity(),PlaceActivity.class);
                    intent.putExtra("type",data.type);
                    intent.putExtra("uniqueid",data.uniqueid);
                    intent.putExtra("name",data.name);
                    intent.putExtra("address",data.address);
                    intent.putExtra("tel",data.tel);
                    intent.putExtra("homepage",data.homepage);
                    intent.putExtra("mainroad",data.mainroad);
                    intent.putExtra("parking",data.parking);
                    intent.putExtra("mainflat",data.mainflat);
                    intent.putExtra("elevator",data.elevator);
                    intent.putExtra("toilet",data.toilet);
                    intent.putExtra("room",data.room);
                    intent.putExtra("seat",data.seat);
                    intent.putExtra("ticket",data.ticket);
                    intent.putExtra("blind",data.blind);
                    intent.putExtra("deaf",data.deaf);
                    intent.putExtra("guide",data.guide);
                    intent.putExtra("wheelchair",data.wheelchair);
                    startActivity(intent);
                    return false;
                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        gMap = googleMap;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mapView != null)
            mapView.onCreate(savedInstanceState);
    }

}
