package com.tcp.thisable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();
    public ListViewAdapter(){}

    @Override
    public int getCount() {
        return listVO.size();
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
    public View getView(int i, View convertView, ViewGroup parent) {
        final int pos = i;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review,parent,false);
        }

        TextView place_name = convertView.findViewById(R.id.place_name);
        RatingBar rating = convertView.findViewById(R.id.rating);
        TextView comment = convertView.findViewById(R.id.comment);
        Button button = convertView.findViewById(R.id.delete_review);

        ListVO listViewItem = listVO.get(i);

        place_name.setText(listViewItem.getPlace_name());
        rating.setMax(5);
        rating.setRating(listViewItem.getRating());
        comment.setText(listViewItem.getComment());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return convertView;
    }

    public void addVO(String place_name,Float rating,String comment) {
        ListVO item = new ListVO();
        item.setPlace_name(place_name);
        item.setRating(rating);
        item.setComment(comment);

        listVO.add(item);
    }
}
