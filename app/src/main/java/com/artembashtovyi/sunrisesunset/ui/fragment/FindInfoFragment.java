package com.artembashtovyi.sunrisesunset.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artembashtovyi.sunrisesunset.R;
import com.artembashtovyi.sunrisesunset.model.response.Results;
import com.artembashtovyi.sunrisesunset.ui.DashboardActivity;
import com.artembashtovyi.sunrisesunset.ui.reciever.PlaceInfoReceiver;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Artem Bashtovyi on 6/8/18
 */

public class FindInfoFragment extends Fragment implements PlaceInfoReceiver {

    private static final String TAG = "FindInfoFragment";
    private LocationInfoCallback callback;

    private TextView sunriseTv;
    private TextView sunsetTv;
    private TextView dayLengthTv;
    private View rootInclude;

    public FindInfoFragment() {
    }

    public static FindInfoFragment createFragment() {
        return new FindInfoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;

        if (context instanceof DashboardActivity){
            a=(Activity) context;
            try {
                callback = (LocationInfoCallback) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a + " must implement LocationInfoCallback");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_info_fragment, container, false);
        rootInclude = rootView.findViewById(R.id.find_info_include_root);

        sunriseTv = rootInclude.findViewById(R.id.sunrise_text_view);
        sunsetTv = rootInclude.findViewById(R.id.sunset_text_view);
        dayLengthTv = rootInclude.findViewById(R.id.day_length_text_view);


        rootInclude.setVisibility(View.INVISIBLE);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // cities filter
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng location = place.getLatLng();
                Log.i(TAG,"Place: " + place.getName() + " latitude=" + location.latitude +
                        "longitude=" + location.longitude );

                callback.onLocationLoaded(FindInfoFragment.this,location.latitude,location.longitude);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        return rootView;
    }

    @Override
    public void onPlaceLoaded(Results response) {
        rootInclude.setVisibility(View.VISIBLE);
        sunsetTv.setText(response.getSunset());
        sunriseTv.setText(response.getSunrise());
        dayLengthTv.setText(response.getDayLength());
        Log.i(TAG, "onPlaceLoaded: FIndInfoFragment");
    }

    // prevent memory leaks
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
