package com.tcp.thisable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.mViewHolder> {
    private ArrayList<Data> datalist;

    public class mViewHolder extends RecyclerView.ViewHolder {
       TextView textView_name;
       TextView textView_address;
       TextView textView_distance;
       RatingBar ratingBar;

        public mViewHolder(View view) {
            super(view);

            textView_name = view.findViewById(R.id.textView_name_LML);
            textView_address = view.findViewById(R.id.textView_address_LML);
            textView_distance = view.findViewById(R.id.textView_distanceLML);
            ratingBar = view.findViewById(R.id.ratingBar_LML);
        }
    }

    public MainListAdapter(ArrayList<Data> datalist) {
        this.datalist = datalist;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_main_list, parent, false);

        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.textView_name.setText(datalist.get(position).name);
        holder.textView_address.setText(datalist.get(position).address);

        holder.ratingBar.setMax(5);
        holder.ratingBar.setRating((float) datalist.get(position).rating.sum / (float) datalist.get(position).rating.count);
    }

    @Override
    public int getItemCount() {
        if(datalist != null) return datalist.size();
        else return 0;
    }
}