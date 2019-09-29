package com.tcp.thisable.Dao;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("type") public String type;
    @SerializedName("coordinates") public double[] coordinates;
}
