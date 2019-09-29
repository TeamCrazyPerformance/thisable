package com.tcp.thisable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.mViewHolder> {
    private ArrayList<Review> reviewlist;
    private int type;
    private String userid;

    public class mViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_content;
        RatingBar ratingBar;
        ImageButton button;

        public mViewHolder(View view) {
            super(view);

            textView_name = view.findViewById(R.id.place_name);
            textView_content = view.findViewById(R.id.comment);
            ratingBar = view.findViewById(R.id.rating);
            button = view.findViewById(R.id.delete_review);
        }
    }

    public ListAdapter(ArrayList<Review> reviewlist, int type, String userid) {
        this.reviewlist = reviewlist;
        this.type = type;
        this.userid = userid;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_review, parent, false);

        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        if(type == 0)
            holder.textView_name.setText(reviewlist.get(position).name);

        else
            holder.textView_name.setText(reviewlist.get(position).username);

        holder.textView_content.setText(reviewlist.get(position).content);

        holder.ratingBar.setMax(5);
        holder.ratingBar.setRating(reviewlist.get(position).rating);

        final int pos = position;

        if(type == 0) {
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<Integer> res = NetRetrofit.getInstance().getService().deleteReview(reviewlist.get(pos).id, reviewlist.get(pos).userid);
                    res.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            reviewlist.remove(pos);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
            });
        }
        else
            holder.button.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if(reviewlist != null) return reviewlist.size();
        else return 0;
    }
}