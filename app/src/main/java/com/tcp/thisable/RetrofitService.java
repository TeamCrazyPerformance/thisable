package com.tcp.thisable;

import com.google.gson.JsonArray;
import com.tcp.thisable.Dao.Data;
import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("review/user/myreview/{user}")
    Call<ArrayList<Review>> getListRepos(@Path("user") String id);

    @GET("data/{type}")
    Call<ArrayList<Data>> getNearData(@Path("type") String type, @Query("distance") Integer distance, @Query("longitude") Float longitude, @Query("latitude") Float latitude);

    @FormUrlEncoded
    @POST("data/{type}")
    Call<ArrayList<Data>> getSearchData(@Path("type") String type, @Field("longitude") Float longitude, @Field("latitude") Float latitude, @Field("text") String text, @Field("query") String query);
}
