package com.artembashtovyi.sunrisesunset.ui.reciever;

import com.artembashtovyi.sunrisesunset.model.response.Results;

/**
 *  Interface-marker for fragments which need to receive data
 *  about place(no matter current place or another place)
 */

public interface PlaceInfoReceiver {
    /**
     * @param response - info about place
     */
    void onPlaceLoaded(Results response);
}
