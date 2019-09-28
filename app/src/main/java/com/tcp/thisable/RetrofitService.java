package com.tcp.thisable;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("review/{user}/myreview/5d8f29b5c69ca87bf20404f7")
    Call<ArrayList<JsonObject>> getListRepos(@Path("user") String id);

}

