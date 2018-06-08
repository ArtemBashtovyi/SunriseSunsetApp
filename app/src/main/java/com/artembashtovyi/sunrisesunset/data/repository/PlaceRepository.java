package com.artembashtovyi.sunrisesunset.data.repository;

import com.artembashtovyi.sunrisesunset.data.callbacks.DataSourceCallbacks;

/**
 * Created by Artem Bashtovyi on 6/6/18
 */

public interface PlaceRepository {

    void getSunInfo(DataSourceCallbacks.LoadSunInfoCallback callback, double latitude, double longitude);
}
