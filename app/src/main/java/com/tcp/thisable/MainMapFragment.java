package com.tcp.thisable;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    ConstraintLayout constraintLayout_bottom;
    TextView bottom_name;
    TextView bottom_address;

    ArrayList<Marker> markers = new ArrayList<>();

    public MainMapFragment() {
    }

    public void updateUi(ArrayList<Data> d) {
        gMap.clear();

        listarray = d;

        int i = 0;
        for(Data data: listarray) {
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(data.location.coordinates[1], data.location.coordinates[0])).title(data.name);
            markers.add(gMap.addMarker(markerOptions));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);

        constraintLayout_bottom = view.findViewById(R.id.constraintLayout_bottomFMM);
        bottom_address = view.findViewById(R.id.textView_addressFMM);
        bottom_name = view.findViewById(R.id.textView_nameFMM);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        gMap = googleMap;

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final int i = markers.indexOf(marker);
                constraintLayout_bottom.setVisibility(View.VISIBLE);
                bottom_name.setText(listarray.get(i).name);
                bottom_address.setText(listarray.get(i).address);

                constraintLayout_bottom.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(),PlaceActivity.class);
                                intent.putExtra("type",listarray.get(i).type);
                                intent.putExtra("uniqueid",listarray.get(i).uniqueid);
                                intent.putExtra("name",listarray.get(i).name);
                                intent.putExtra("address",listarray.get(i).address);
                                intent.putExtra("tel",listarray.get(i).tel);
                                intent.putExtra("homepage",listarray.get(i).homepage);
                                intent.putExtra("mainroad",listarray.get(i).mainroad);
                                intent.putExtra("parking",listarray.get(i).parking);
                                intent.putExtra("mainflat",listarray.get(i).mainflat);
                                intent.putExtra("elevator",listarray.get(i).elevator);
                                intent.putExtra("toilet",listarray.get(i).toilet);
                                intent.putExtra("room",listarray.get(i).room);
                                intent.putExtra("seat",listarray.get(i).seat);
                                intent.putExtra("ticket",listarray.get(i).ticket);
                                intent.putExtra("blind",listarray.get(i).blind);
                                intent.putExtra("deaf",listarray.get(i).deaf);
                                intent.putExtra("guide",listarray.get(i).guide);
                                intent.putExtra("wheelchair",listarray.get(i).wheelchair);
                                startActivity(intent);
                            }
                        }
                );

                return false;
            }
        });

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                constraintLayout_bottom.setVisibility(View.GONE);
            }
        });
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
