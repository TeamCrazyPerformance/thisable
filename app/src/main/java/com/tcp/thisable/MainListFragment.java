package com.tcp.thisable;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainListFragment extends Fragment {

    ArrayList<Data> listarray = new ArrayList<>();
    RecyclerView recyclerView;
    MainListAdapter adapter;

    public MainListFragment() {
    }

    public void updateUi(ArrayList<Data> d, LatLng currentLocation) {
        listarray.clear();
        listarray.addAll(d);
        if(adapter != null) {
            adapter.setCurrentLocation(currentLocation);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_FML);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Location currentLocation = null;

        if(getArguments() != null) {
            currentLocation = new Location("");
            currentLocation.setLongitude(getArguments().getDouble("longitude"));
            currentLocation.setLatitude(getArguments().getDouble("latitude"));
        }
        adapter = new MainListAdapter(listarray, currentLocation);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
