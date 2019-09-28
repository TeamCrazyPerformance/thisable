package com.tcp.thisable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainListFragment extends Fragment {

    ArrayList<Data> listarray = new ArrayList<>();
    RecyclerView recyclerView;
    MainListAdapter adapter;

    public MainListFragment() {
    }

    public void updateUi(ArrayList<Data> d) {
        listarray.clear();
        listarray.addAll(d);

        if(adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        recyclerView = view.findViewById(R.id.recyvlerview_FML);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MainListAdapter(listarray);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
