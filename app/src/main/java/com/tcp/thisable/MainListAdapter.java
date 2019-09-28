/*package com.tcp.thisable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcp.thisable.Dao.Data;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.mViewHolder> {
    private ArrayList<Data> list;

    public class mViewHolder extends RecyclerView.ViewHolder {
       // protected TextView

        public mViewHolder(View view) {
            super(view);

        }
    }

    public MainListAdapter(ArrayList<Data> list) {
        this.list = list;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        //CustomViewHolder viewHolder = new CustomViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();
        else return 0;
    }
}
*/