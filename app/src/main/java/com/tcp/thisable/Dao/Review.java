package com.tcp.thisable.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Review {
    @SerializedName("uniqueid") public Integer uniqueid;
    @SerializedName("name") public String name;
    @SerializedName("type") public String type;
    @SerializedName("content") public String content;
    @SerializedName("username") public String username;
    @SerializedName("userid") public String userid;
    @SerializedName("rating") public float rating;
    @SerializedName("date") public Date date;
}
