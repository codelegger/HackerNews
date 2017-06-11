package com.propertyguru.hackernews.hackernews.data.server;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by uba on 09/06/2017.
 */


public class RetroFitManager {


    String ENDPOINT = "https://hacker-news.firebaseio.com/v0/";


    private void setup()
    {
        //todo
    }

    public FeedService fsInstance() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).setConverter(new GsonConverter(new GsonBuilder().create())).build();
        return restAdapter.create(FeedService.class);
    }




}
