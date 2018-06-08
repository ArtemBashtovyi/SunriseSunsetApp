package com.artembashtovyi.sunrisesunset.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artem Bashtovyi on 6/7/18
 */

public class Results {

    @SerializedName("sunrise")
    private String sunrise;

    @SerializedName("sunset")
    private String sunset;

    @SerializedName("day_length")
    private String dayLength;

    public Results(String sunrise, String sunset, String dayLength) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dayLength = dayLength;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getDayLength() {
        return dayLength;
    }



    @Override
    public String toString() {
        return "Results{" +
                "sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", dayLength='" + dayLength + '\'' +
                '}';
    }
}
