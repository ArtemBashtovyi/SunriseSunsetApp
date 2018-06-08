package com.artembashtovyi.sunrisesunset.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artembashtovyi.sunrisesunset.R;
import com.artembashtovyi.sunrisesunset.model.response.Results;
import com.artembashtovyi.sunrisesunset.ui.DashboardActivity;
import com.artembashtovyi.sunrisesunset.ui.reciever.PlaceInfoReceiver;

/**
 * Created by Artem Bashtovyi on 6/8/18
 */

public class UserInfoFragment extends Fragment implements PlaceInfoReceiver {
    private static final String TAG = "UserInfoFragment" ;

    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 1024;
    private static final int MIN_TIME_UPDATE_INTERVAL = 1000 * 60 * 5; // 5 minutes
    private static final int MIN_DISTANCE_METERS_UPDATE = 500; // 500 meters

    private LocationInfoCallback callback;
    private LocationManager locationManager;
    private Context context;
    private ProgressDialog dialog;

    private boolean isLocationPermissionGranted = false;

    // not best choice
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG,"Received location :[latitude,longitude]=["
                    + location.getLatitude() + "," + location.getLongitude() +"]");
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            // send data to presenter through activity
            callback.onLocationLoaded(UserInfoFragment.this,latitude,longitude);
            hideLoadingDialog();
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private TextView sunriseTv;
    private TextView sunsetTv;
    private TextView dayLengthTv;

    public UserInfoFragment() {
    }

    public static UserInfoFragment createFragment() {
        return new UserInfoFragment();
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
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_info_fragment, container, false);
        View rootInclude = rootView.findViewById(R.id.user_info_include_root);

        // FIXME : move to BaseFragment
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading...");
        dialog.setCancelable(false);

        Button button = rootView.findViewById(R.id.button);
        sunriseTv = rootInclude.findViewById(R.id.sunrise_text_view);
        sunsetTv = rootInclude.findViewById(R.id.sunset_text_view);
        dayLengthTv = rootInclude.findViewById(R.id.day_length_text_view);

        button.setOnClickListener(view -> getDeviceLocation());

        return rootView;
    }

    // init here because fragment can return from the back stack
    // location manager here for battery safety
    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    // Receive permission state result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                Log.i(TAG,"Location permission received");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermissionGranted = true;
                } else {
                    Log.e(TAG,"Error response of permissions");
                }
            }
        }
        updateLocation();
    }

    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permissions haven't geared
            isLocationPermissionGranted = true;
            // GPS isn't enabled
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showPermissionsAlert();
            } else {
                updateLocation();
            }

        } else {
            // permissions geared
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_LOCATION);
            Log.i(TAG,"getPermissions");
        }
    }

    // male request
    private void updateLocation() {
        try {
            if (isLocationPermissionGranted) {
                showLoadingDialog();
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                        MIN_TIME_UPDATE_INTERVAL,
                        MIN_DISTANCE_METERS_UPDATE,
                        locationListener);
                Log.i(TAG, "updateLocation: received");
            } else {
                Log.e(TAG,"Error");
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
            Toast.makeText(context,"Oops,error!Please try again", Toast.LENGTH_LONG).show();
        }
    }

    // show dialog for API < 23
    private void showPermissionsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Enable Location");
        alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
        alertDialog.setPositiveButton("Location Settings", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        });
        alertDialog.setNegativeButton("Ok", (dialog, which) -> dialog.cancel());
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    // receive place response
    @Override
    public void onPlaceLoaded(Results response) {
        sunsetTv.setText(response.getSunset());
        sunriseTv.setText(response.getSunrise());
        dayLengthTv.setText(response.getDayLength());
    }

    private void showLoadingDialog(){
        dialog.show();
    }

    private void hideLoadingDialog(){
        dialog.hide();
    }

    // Optimization in usage locationManager for saving battery
    // Furthermore all references
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        dialog.dismiss();
        locationListener = null;
        locationManager = null;
        dialog = null;
    }

    // prevent memory leaks
    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
        callback = null;
    }


}
