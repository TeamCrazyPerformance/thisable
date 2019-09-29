package com.tcp.thisable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.TedPermission;
import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainMapFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;

    private ArrayList<Data> listarray = new ArrayList<>();
    private GoogleMap gMap;
    private ConstraintLayout constraintLayout_bottom;
    private TextView bottom_name;
    private TextView bottom_address;
    private TextView bottom_distance;
    private RatingBar bottom_rating;

    private FusedLocationProviderClient fusedLocationProviderClient;

    public LatLng currentLocation = null;

    private ArrayList<Marker> markers = new ArrayList<>();

    public MainMapFragment() {
    }

    public void updateUi(ArrayList<Data> d) {
        gMap.clear();

        listarray = d;

        int size = listarray.size();
        float sum_longtitude = 0;
        float sum_latitude = 0;

        for(Data data: listarray) {
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(data.location.coordinates[1], data.location.coordinates[0])).title(data.name);
            markers.add(gMap.addMarker(markerOptions));

            sum_longtitude += data.location.coordinates[0];
            sum_latitude += data.location.coordinates[1];
        }

        moveCamera(new LatLng(sum_latitude/size, sum_longtitude/size), 12);
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
        bottom_distance = view.findViewById(R.id.textView_distanceFMM);
        bottom_rating = view.findViewById(R.id.ratingBar_FMM);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.56, 126.97)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if( hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

            Log.d("asdsad", "Asdasdasds");
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.getUiSettings().setMapToolbarEnabled(false);

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                    setCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            });

            gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                            setCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                        }
                    });
                    return false;
                }
            });
        }

        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.56, 126.97)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final int i = markers.indexOf(marker);

                constraintLayout_bottom.setVisibility(View.VISIBLE);
                bottom_name.setText(listarray.get(i).name);
                bottom_address.setText(listarray.get(i).address);


                bottom_rating.setRating(listarray.get(i).rating.sum / (float)listarray.get(i).rating.count);

                if(currentLocation == null)
                    bottom_distance.setText("");
                else {
                    Location location = new Location("");
                    location.setLongitude(listarray.get(i).location.coordinates[0]);
                    location.setLatitude(listarray.get(i).location.coordinates[1]);

                    Location curlocation = new Location("");
                    curlocation.setLongitude(currentLocation.longitude);
                    curlocation.setLatitude(currentLocation.latitude);

                    int distance = Math.round(curlocation.distanceTo(location));

                    if(distance > 1000) {
                        bottom_distance.setText(Math.round(distance / 1000f * 10) / 10f + "km");
                    }

                    else {
                        bottom_distance.setText(distance + "m");
                    }

                }

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
                                intent.putExtra("rating_sum",listarray.get(i).rating.sum);
                                intent.putExtra("rating_count",listarray.get(i).rating.count);
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

    private void moveCamera(LatLng location, float zoom)
    {
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    private void setCurrentLocation(LatLng location)
    {
        currentLocation = location;
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