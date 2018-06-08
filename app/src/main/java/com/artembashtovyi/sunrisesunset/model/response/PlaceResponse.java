package com.artembashtovyi.sunrisesunset.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artem Bashtovyi on 6/7/18
 */

public class PlaceResponse {

    @SerializedName("results")
    private Results results;

    public PlaceResponse(Results results) {
        this.results = results;
    }

    public Results getResults() {
        return results;
    }

}
