package com.tcp.thisable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.mViewHolder> {
    private ArrayList<Review> reviewlist;
    private int type;

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

    public ListAdapter(ArrayList<Review> reviewlist, int type) {
        this.reviewlist = reviewlist;
        this.type = type;
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
        holder.ratingBar.setStepSize(0.5f);
        holder.ratingBar.setRating(reviewlist.get(position).rating);

        final int pos = position;

        if(type == 0) {
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = view.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("리뷰 삭제");
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Log.d("asd", reviewlist.get(pos).content);
                                    Call<Integer> res = NetRetrofit.getInstance().getService().deleteReview(reviewlist.get(pos).id, reviewlist.get(pos).userid);
                                    res.enqueue(new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                            if(response.isSuccessful()) {
                                                reviewlist.remove(pos);
                                                notifyDataSetChanged();
                                            }
                                            else {
                                                Toast.makeText(context, "리뷰가 삭제되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Integer> call, Throwable t) {
                                            Toast.makeText(context, "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
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