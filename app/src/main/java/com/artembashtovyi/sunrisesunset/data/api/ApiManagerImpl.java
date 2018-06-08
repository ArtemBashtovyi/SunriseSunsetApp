package com.artembashtovyi.sunrisesunset.data.api;

import android.support.annotation.NonNull;

import com.artembashtovyi.sunrisesunset.data.api.service.SunService;
import com.artembashtovyi.sunrisesunset.data.callbacks.DataSourceCallbacks;
import com.artembashtovyi.sunrisesunset.model.response.PlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Artem Bashtovyi on 6/7/18
 */

public class ApiManagerImpl implements ApiManager {

    private SunService sunService;

    public ApiManagerImpl(SunService sunService) {
        this.sunService = sunService;
    }

    @Override
    public void loadSunInfo(DataSourceCallbacks.LoadSunInfoCallback callback, double latitude, double longitude) {
        Call<PlaceResponse> call = sunService.getSunInfo(latitude,longitude);

        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                if (response.isSuccessful()) {
                    callback.onLoadSunInfo(response.body());
                }else {
                    callback.onLoadSunInfoError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                callback.onLoadSunInfoError();
            }
        });
    }
}
