package com.tcp.thisable;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.mViewHolder> {
    private ArrayList<Data> datalist;
    private Location currentLocation = null;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_name;
        TextView textView_address;
        TextView textView_distance;
        RatingBar ratingBar;

        public mViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            textView_name = view.findViewById(R.id.textView_name_LML);
            textView_address = view.findViewById(R.id.textView_address_LML);
            textView_distance = view.findViewById(R.id.textView_distanceLML);
            ratingBar = view.findViewById(R.id.ratingBar_LML);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PlaceActivity.class);
            intent.putExtra("type", datalist.get(getLayoutPosition()).type);
            intent.putExtra("uniqueid", datalist.get(getLayoutPosition()).uniqueid);
            intent.putExtra("name", datalist.get(getLayoutPosition()).name);
            intent.putExtra("address", datalist.get(getLayoutPosition()).address);
            intent.putExtra("tel", datalist.get(getLayoutPosition()).tel);
            intent.putExtra("homepage", datalist.get(getLayoutPosition()).homepage);
            intent.putExtra("mainroad", datalist.get(getLayoutPosition()).mainroad);
            intent.putExtra("parking", datalist.get(getLayoutPosition()).parking);
            intent.putExtra("mainflat", datalist.get(getLayoutPosition()).mainflat);
            intent.putExtra("elevator", datalist.get(getLayoutPosition()).elevator);
            intent.putExtra("toilet", datalist.get(getLayoutPosition()).toilet);
            intent.putExtra("room", datalist.get(getLayoutPosition()).room);
            intent.putExtra("seat", datalist.get(getLayoutPosition()).seat);
            intent.putExtra("ticket", datalist.get(getLayoutPosition()).ticket);
            intent.putExtra("blind", datalist.get(getLayoutPosition()).blind);
            intent.putExtra("deaf", datalist.get(getLayoutPosition()).deaf);
            intent.putExtra("guide", datalist.get(getLayoutPosition()).guide);
            intent.putExtra("wheelchair", datalist.get(getLayoutPosition()).wheelchair);
            view.getContext().startActivity(intent);
        }
    }

    public MainListAdapter(ArrayList<Data> datalist, Location currentLocation) {
        this.datalist = datalist;
        this.currentLocation = currentLocation;
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

        if(currentLocation == null)
            holder.textView_distance.setText("");
        else {
            Location location = new Location("");
            location.setLongitude(datalist.get(position).location.coordinates[0]);
            location.setLatitude(datalist.get(position).location.coordinates[1]);

            int distance = Math.round(currentLocation.distanceTo(location));

            if(distance > 1000) {
                holder.textView_distance.setText(Math.round(distance / 1000f * 10) / 10f + "km");
            }

            else {
                holder.textView_distance.setText(distance + "m");
            }

        }
    }

    @Override
    public int getItemCount() {
        if(datalist != null) return datalist.size();
        else return 0;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = new Location("");
        this.currentLocation.setLongitude(currentLocation.longitude);
        this.currentLocation.setLatitude(currentLocation.latitude);
    }
}