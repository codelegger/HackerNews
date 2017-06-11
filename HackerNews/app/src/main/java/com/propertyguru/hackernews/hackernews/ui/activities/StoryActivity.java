package com.propertyguru.hackernews.hackernews.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.propertyguru.hackernews.hackernews.R;
import com.propertyguru.hackernews.hackernews.data.manager.ApiHandler;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.ui.HackerNewsApplication;
import com.propertyguru.hackernews.hackernews.ui.adapters.StoriesListAdapter;
import com.propertyguru.hackernews.hackernews.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/*
 * Created by Uba on 6/7/17.
 */

public class StoryActivity extends AppCompatActivity {
    private List<Subscription> mSubscriptions = new ArrayList<>();
    private ArrayList<Story> mStories;
    private StoriesListAdapter storiesListAdapter;

    //UI elements
    private RecyclerView rvStoryContainer;
    private RelativeLayout pbLoadingLayout;
    private SwipeRefreshLayout srContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //extract UI elements from Xml
        rvStoryContainer = (RecyclerView) findViewById(R.id.rvStoryContainer);
        pbLoadingLayout = (RelativeLayout) findViewById(R.id.pbLoadingLayout);
        srContainer = (SwipeRefreshLayout) findViewById(R.id.srContainer);

        init();
    }

    private void init() {

        //Show Loading at start
        pbLoadingLayout.setVisibility(View.VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle(HackerNewsApplication.getsInstance().getString(R.string.main_screen_title));
        }

        mStories = new ArrayList<Story>();

        //Init List Adapter
        storiesListAdapter = new StoriesListAdapter(this, mStories);

        //Set Layout Manager on Recycle View
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvStoryContainer.setLayoutManager(linearLayoutManager);

        //Attach Adapter with Recycle View
        rvStoryContainer.setAdapter(storiesListAdapter);

        //Set Pull to refresh Layout
        srContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srContainer.setRefreshing(true);

                //Stop/Clear All Existing API Subscriptions
                clearAllSubscription();

                //Let adapter know that data will be refreshed from 0
                storiesListAdapter.setIsRefreshing(true);

                //Clear Existing Local Data
                mStories.clear();
                storiesListAdapter.updateDataSet(mStories);

                //Fetch data again from 0
                fetchData();
            }
        });

        fetchData();

    }

    private void fetchData() {
        //Show Error Message if Network is not Available
        if(!AppUtils.isNetworkConnected()){
            AppUtils.showAlertMessage(StoryActivity.this,
                    StoryActivity.this.getString(R.string.error_string),
                    StoryActivity.this.getString(R.string.pull_to_refresh_msg));
            hideLoadingUiElements();
            return;
        }

        //Start Fteching data from API
        ApiHandler _dmInstance = new ApiHandler();
        mSubscriptions.add(_dmInstance.getTopStories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(_dmInstance.getScheduler())
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoadingUiElements();
                        AppUtils.showAlertMessage(StoryActivity.this,
                                StoryActivity.this.getString(R.string.error_string),
                                StoryActivity.this.getString(R.string.pull_to_refresh_msg));
                    }

                    @Override
                    public void onNext(Story post) {
                        hideLoadingUiElements();
                        mStories.add(post);
                        sortStories();
                        if (storiesListAdapter != null) {
                            storiesListAdapter.updateDataSet(mStories);
                        }
                        //Hide Loading if its not already hidden as List is not Empty anymore
                        hideLoadingUiElements();
                    }
                }));
    }

    private void hideLoadingUiElements() {
        if (pbLoadingLayout != null && pbLoadingLayout.getVisibility() == View.VISIBLE) {
            pbLoadingLayout.setVisibility(View.GONE);
        }

        //Hide Pull to Refresh Layout
        if(srContainer != null){
            srContainer.setRefreshing(false);
        }
    }

    //Stop/Clear All API Subscriptions which are Active at the moment
    private void clearAllSubscription(){
        if(mSubscriptions != null && !mSubscriptions.isEmpty()){
            for (int i = 0; i < mSubscriptions.size(); i++) {
                if(!mSubscriptions.get(i).isUnsubscribed()){
                    mSubscriptions.get(i).unsubscribe();
                }
            }
        }
    }

    private void sortStories(){
        //Sort Bit rates in ascending order
        Collections.sort(mStories, new Comparator<Story>() {
            @Override
            public int compare(Story story1, Story stor2) {
                return stor2.time.compareTo(story1.time);
            }
        });

    }
}
