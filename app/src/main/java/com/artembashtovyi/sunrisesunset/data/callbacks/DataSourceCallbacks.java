package com.artembashtovyi.sunrisesunset.data.callbacks;

import com.artembashtovyi.sunrisesunset.model.response.PlaceResponse;

/**
 *  Callbacks can be exist for api,DB and any cache
 */


public interface DataSourceCallbacks {

    interface LoadSunInfoCallback {

        void onLoadSunInfo(PlaceResponse response);

        void onLoadSunInfoError();

    }
}
