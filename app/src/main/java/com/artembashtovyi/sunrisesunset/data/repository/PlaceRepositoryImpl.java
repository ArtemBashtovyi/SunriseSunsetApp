package com.artembashtovyi.sunrisesunset.data.repository;

import com.artembashtovyi.sunrisesunset.data.api.ApiManager;
import com.artembashtovyi.sunrisesunset.data.callbacks.DataSourceCallbacks;

/**
 * Created by Artem Bashtovyi on 6/6/18
 */

class PlaceRepositoryImpl implements PlaceRepository {

    private ApiManager apiManager;

    public PlaceRepositoryImpl(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    @Override
    public void getSunInfo(DataSourceCallbacks.LoadSunInfoCallback callback, double latitude, double longitude) {
        // perhaps here will be caching the data
        apiManager.loadSunInfo(callback,latitude,longitude);
    }
}
