package com.tcp.thisable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewAdapter2 extends BaseAdapter {
    private ArrayList<Review> listVO = null;
    private int count = 0;
    LayoutInflater inflater = null;

    public ListViewAdapter2(ArrayList<Review> listVO){
        this.listVO = listVO;
        this.count = listVO.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return listVO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.review2, parent, false);
        }

        TextView place_name = convertView.findViewById(R.id.place_name);
        RatingBar rating = convertView.findViewById(R.id.rating);
        TextView comment = convertView.findViewById(R.id.comment);

        place_name.setText(listVO.get(i).name);
        rating.setRating(listVO.get(i).rating);
        comment.setText(listVO.get(i).content);

        return convertView;
    }

}