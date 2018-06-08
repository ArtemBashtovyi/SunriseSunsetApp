package com.artembashtovyi.sunrisesunset.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.artembashtovyi.sunrisesunset.R;
import com.artembashtovyi.sunrisesunset.data.repository.PlaceRepositoryFactory;
import com.artembashtovyi.sunrisesunset.model.response.Results;
import com.artembashtovyi.sunrisesunset.presenter.DashboardPresenter;
import com.artembashtovyi.sunrisesunset.ui.adapter.FragmentAdapter;
import com.artembashtovyi.sunrisesunset.ui.fragment.FindInfoFragment;
import com.artembashtovyi.sunrisesunset.ui.fragment.LocationInfoCallback;
import com.artembashtovyi.sunrisesunset.ui.fragment.UserInfoFragment;
import com.artembashtovyi.sunrisesunset.ui.reciever.PlaceInfoReceiver;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends MvpAppCompatActivity implements DashboardView,LocationInfoCallback {
    private static final String TAG = "DashboardActivity:";


    @InjectPresenter
    DashboardPresenter presenter;

    // in UI because, it's view and could be part of Framework
    private PlaceInfoReceiver currentReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //just create fragments
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(UserInfoFragment.createFragment());
        fragments.add(FindInfoFragment.createFragment());

        FragmentAdapter mSectionsPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @ProvidePresenter
    DashboardPresenter provideDetailsPresenter() {
        PlaceRepositoryFactory factory = new PlaceRepositoryFactory();
        return new DashboardPresenter(factory.create());
    }


    @Override
    public void onLocationLoaded(PlaceInfoReceiver receiver,double latitude, double longitude) {
        currentReceiver = receiver;
        presenter.loadCurrentPlaceInfo(latitude,longitude);
        Log.i(TAG,"OnLocationLoaded");
    }

    // send data to any view which made request
    @Override
    public void showPlaceInfo(Results response) {
        if (currentReceiver != null) {
            sendPlaceToReceiver(currentReceiver, response);
        }
    }

    // send place to current receiver
    private void sendPlaceToReceiver(PlaceInfoReceiver receiver,Results results) {
        receiver.onPlaceLoaded(results);
        Log.i(TAG,"Send data to receiver");
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentReceiver = null;
    }
}
