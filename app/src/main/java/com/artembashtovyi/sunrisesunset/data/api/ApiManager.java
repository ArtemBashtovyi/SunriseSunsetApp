package com.artembashtovyi.sunrisesunset.data.api;

import com.artembashtovyi.sunrisesunset.data.callbacks.DataSourceCallbacks;

/**
 * Created by Artem Bashtovyi on 6/7/18
 */

public interface ApiManager {

    void loadSunInfo(DataSourceCallbacks.LoadSunInfoCallback callback,
                     double latitude, double longitude);

}
