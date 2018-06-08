package com.artembashtovyi.sunrisesunset.data.repository;

import com.artembashtovyi.sunrisesunset.data.api.ApiFactory;
import com.artembashtovyi.sunrisesunset.data.api.ApiManager;
import com.artembashtovyi.sunrisesunset.data.api.ApiManagerImpl;
import com.artembashtovyi.sunrisesunset.data.api.service.SunService;
import com.artembashtovyi.sunrisesunset.data.repository.factory.RepositoryFactory;

/**
 * Created by Artem Bashtovyi on 6/8/18
 */

public class PlaceRepositoryFactory implements RepositoryFactory<PlaceRepository> {

    @Override
    public PlaceRepository create() {
        SunService sunService = ApiFactory.createSunService();
        ApiManager apiManager = new ApiManagerImpl(sunService);
        return new PlaceRepositoryImpl(apiManager);
    }
}
