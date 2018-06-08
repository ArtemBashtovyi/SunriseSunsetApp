package com.artembashtovyi.sunrisesunset;

import android.app.Application;

/**
 * Created by felix on 6/6/18
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);*/
    }
}
