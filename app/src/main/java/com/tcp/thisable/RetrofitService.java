package com.tcp.thisable;

import com.tcp.thisable.Dao.Data;
import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("review/user/myreview/{user}")
    Call<ArrayList<Review>> getListRepos(@Path("user") String id);

    @FormUrlEncoded
    @POST("user")
    Call<String> signIn(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("user/signup")
    Call<String> signUp(@Field("email") String email,@Field("password") String password,@Field("name") String name);


    @GET("review/{type}/{uniqueid}")
    Call<ArrayList<Review>> getReviewList(@Path("type") String type, @Path("uniqueid") int id);

    @FormUrlEncoded
    @POST("data/{type}")
    Call<ArrayList<Data>> getSearchData(@Path("type") String type, @Field("longitude") Double longitude, @Field("latitude") Double latitude, @Field("text") String text, @Field("query") String query);

    @FormUrlEncoded
    @POST("data2/{type}")
    Call<ArrayList<Data>> getSearchData2(@Path("type") String type, @Field("longitude") Double longitude, @Field("latitude") Double latitude, @Field("text") String text);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "review/", hasBody = true)
    Call<Integer> deleteReview(@Field("id") String id, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("review/{type}")
    Call<Review> writeReview(@Path("type") String type, @Field("uniqueid") int uniqueid, @Field("content") String content, @Field("userid") String userid, @Field("name") String name, @Field("rating") float rating);
    
}
