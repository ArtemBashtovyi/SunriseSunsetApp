package com.artembashtovyi.sunrisesunset.ui.fragment;

import com.artembashtovyi.sunrisesunset.ui.reciever.PlaceInfoReceiver;

/**
 *  Every who want to get info about place by lat/long
 *  need to send lat/long to the activity
 */

public interface LocationInfoCallback {

    /**
     * @param receiver - any receiver who want to get result
     * @param latitude - current
     * @param longitude - current
     */
    void onLocationLoaded(PlaceInfoReceiver receiver,double latitude, double longitude);

}

