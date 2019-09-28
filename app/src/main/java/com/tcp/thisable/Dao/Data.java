package com.tcp.thisable.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Data {
    @SerializedName("type") public String type;
    @SerializedName("uniqueid") public Integer uniqueid;
    @SerializedName("name") public String name;
    @SerializedName("gu") public String gu;
    @SerializedName("address") public String address;
    @SerializedName("tel") public String tel;
    @SerializedName("homepage") public String homepage;
    @SerializedName("mainroad") public Boolean mainroad;
    @SerializedName("parking") public Boolean parking;
    @SerializedName("mainflat") public Boolean mainflat;
    @SerializedName("elevator") public Boolean elevator;
    @SerializedName("toilet") public Boolean toilet;
    @SerializedName("room") public Boolean room;
    @SerializedName("seat") public Boolean seat;
    @SerializedName("ticket") public Boolean ticket;
    @SerializedName("blind") public Boolean blind;
    @SerializedName("deaf") public Boolean deaf;
    @SerializedName("guide") public Boolean guide;
    @SerializedName("wheelchair") public Boolean wheelchair;
    @SerializedName("location") public Location location;
    @SerializedName("rating") public Rating rating;
}
