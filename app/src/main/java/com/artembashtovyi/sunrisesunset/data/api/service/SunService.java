package com.artembashtovyi.sunrisesunset.data.api.service;

import com.artembashtovyi.sunrisesunset.model.response.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Artem Bashtovyi on 6/7/18
 */


public interface SunService {

    @GET("json")
    Call<PlaceResponse> getSunInfo(@Query("lat") double latitude,
                                   @Query("lng") double longitude);

}
