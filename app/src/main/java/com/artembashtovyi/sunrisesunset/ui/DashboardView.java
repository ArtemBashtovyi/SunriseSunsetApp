package com.artembashtovyi.sunrisesunset.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.artembashtovyi.sunrisesunset.model.response.Results;

/**
 * Created by felix on 6/6/18
 */

public interface DashboardView extends MvpView {

    void showPlaceInfo(Results response);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError();
}
