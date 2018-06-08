package com.artembashtovyi.sunrisesunset.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.artembashtovyi.sunrisesunset.data.callbacks.DataSourceCallbacks;
import com.artembashtovyi.sunrisesunset.data.repository.PlaceRepository;
import com.artembashtovyi.sunrisesunset.model.response.PlaceResponse;
import com.artembashtovyi.sunrisesunset.ui.DashboardView;

/**
 * Created by felix on 6/6/18
 *  One presenter for Activity and two fragments, because Fragments use same api request
 */

@InjectViewState
public class DashboardPresenter extends MvpPresenter<DashboardView> {

    private PlaceRepository repository;

    public DashboardPresenter(PlaceRepository repository) {
        this.repository = repository;
    }

    // load PlaceInfo by lat/long
    public void loadCurrentPlaceInfo(double latitude, double longitude) {
        repository.getSunInfo(new DataSourceCallbacks.LoadSunInfoCallback() {
            @Override
            public void onLoadSunInfo(PlaceResponse response) {
                if (response.getResults() != null) {
                    getViewState().showPlaceInfo(response.getResults());
                } else getViewState().showError();
            }

            @Override
            public void onLoadSunInfoError() {
                getViewState().showError();
            }
        },latitude,longitude);
    }
}
