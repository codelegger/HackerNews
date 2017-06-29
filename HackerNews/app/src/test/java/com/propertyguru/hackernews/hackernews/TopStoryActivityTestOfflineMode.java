package com.propertyguru.hackernews.hackernews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;

import com.propertyguru.hackernews.hackernews.data.manager.ApiHandler;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.data.server.FeedService;
import com.propertyguru.hackernews.hackernews.ui.HackerNewsApplication;
import com.propertyguru.hackernews.hackernews.ui.activities.StoryActivity;
import com.propertyguru.hackernews.hackernews.ui.adapters.StoriesListAdapter;
import com.propertyguru.hackernews.hackernews.utils.TestUtil;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by uba on 11/06/2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class TopStoryActivityTestOfflineMode {
    final static  int commentDepth = 0;
    private ApiHandler mockDataManager;
    private FeedService mockFeederService;
    private Story story;

    private StoryActivity activity;

    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    @Before
    public void setUp() {
        connectivityManager = (ConnectivityManager) HackerNewsApplication.getsInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        Shadows.shadowOf(connectivityManager.getActiveNetworkInfo()).setDetailedState(NetworkInfo.DetailedState.DISCONNECTED);
        Shadows.shadowOf(connectivityManager.getActiveNetworkInfo()).setConnectionStatus(false);
        

        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
        shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());

        NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.DISCONNECTED, ConnectivityManager.TYPE_WIFI, 0, false, false);
        shadowConnectivityManager.setNetworkInfo(ConnectivityManager.TYPE_WIFI, networkInfo);
        //controller = Robolectric.buildActivity(SplashScreenActivity.class).create();
        // mySplasActivity = controller.get();
         //activity = Robolectric.buildActivity(StoryActivity.class).create().start().resume().get();
        activity = Robolectric.setupActivity(StoryActivity.class);




        //AppUtils mockUtils = mock(AppUtils.class);

       // when(AppUtils.isNetworkConnected()).thenReturn(false);




//        ConnectivityManager connectivityManager = mock(ConnectivityManager.class);
//        Shadows.shadowOf(connectivityManager.getActiveNetworkInfo()).setConnectionStatus(false);
//        Shadows.shadowOf(connectivityManager.getActiveNetworkInfo()).setAvailableStatus(false);
//        Shadows.shadowOf(connectivityManager.getActiveNetworkInfo()).setDetailedState(NetworkInfo.DetailedState.DISCONNECTED);
//        when(HackerNewsApplication.getsInstance().getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(null);
    }


//    @Test
//    public void testTopStoriesActivity() throws Exception {
//        Assert.assertEquals(activity.getString(R.string.main_screen_title), activity.getSupportActionBar().getTitle().toString());
//    }
//
//
////    @Test
////    public void testCommentActivityTest() throws Exception {
////
////        StoryActivity activity = Robolectric.buildActivity(StoryActivity.class).create().start().resume().get();
////        Assert.assertEquals(activity.getString(R.string.main_screen_title), activity.getSupportActionBar().getTitle().toString());
////        //assertEquals(ItemManager.SHOW_FETCH_MODE, activity.getFetchMode());//controller.pause().stop().destroy();
////    }
////
//    @Test
//    public void testCommentActivityStart() throws Exception {
//
//        Story mockStoryOne = TestUtil.createMockStory();
//        final List<Story> stories = new ArrayList<>();
//        stories.add(mockStoryOne);
//
//
//        RecyclerView leftDrawer = (RecyclerView) activity.findViewById(R.id.rvStoryContainer);
//
//        assertThat(leftDrawer).isNotNull();
//
//        leftDrawer.measure(0,0);
//        leftDrawer.layout(0,0,100,1000);
//
//
//
//        StoriesListAdapter storiesListAdapter =  new StoriesListAdapter(activity.getApplicationContext(),stories);
//        leftDrawer.setAdapter(storiesListAdapter);
//
//        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
//
//        leftDrawer.findViewHolderForAdapterPosition(0).itemView.performClick();
//
//
//        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
//
//
//        Assert.assertEquals(mockStoryOne,intent.getParcelableExtra(AppConstants.EXTRA_ITEM));
//
//
//    }
//
//

    @Test
    public void testfetchItems() throws Exception {


        Story mockStoryOne = TestUtil.createMockStory();
        final List<Story> stories = new ArrayList<>();
        stories.add(mockStoryOne);

        RecyclerView leftDrawer = (RecyclerView) activity.findViewById(R.id.rvStoryContainer);
        assertThat(leftDrawer).isNotNull();
        leftDrawer.measure(0,0);
        leftDrawer.layout(0,0,100,1000);


//        AppUtils utils = mock(AppUtils.class);
//        when(utils.isNetworkConnected()).thenReturn(true);

        StoriesListAdapter storiesListAdapter =  new StoriesListAdapter(activity.getApplicationContext(),stories);
        leftDrawer.setAdapter(storiesListAdapter);
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        leftDrawer.findViewHolderForAdapterPosition(0).itemView.performClick();

        leftDrawer.scrollTo(0,0);
        Assert.assertEquals(0, Shadows.shadowOf(leftDrawer).getScrollX());

    }
//
//    @Test
//    public void testScrollStories() throws Exception {
//
//        Story mockStoryOne = TestUtil.createMockStory();
//        final List<Story> stories = new ArrayList<>();
//        stories.add(mockStoryOne);
//
//        RecyclerView leftDrawer = (RecyclerView) activity.findViewById(R.id.rvStoryContainer);
//        assertThat(leftDrawer).isNotNull();
//        leftDrawer.measure(0,0);
//        leftDrawer.layout(0,0,100,1000);
//
//
//
//        StoriesListAdapter storiesListAdapter =  new StoriesListAdapter(activity.getApplicationContext(),stories);
//        leftDrawer.setAdapter(storiesListAdapter);
//        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
//
//        leftDrawer.findViewHolderForAdapterPosition(0).itemView.performClick();
//
//        leftDrawer.scrollTo(0,0);
//        Assert.assertEquals(0, Shadows.shadowOf(leftDrawer).getScrollX());
//
//    }
//
//
//
//    @Test
//    public void testSortStories() throws Exception {
//
//        Story mockStoryOne = TestUtil.createMockStory();
//        Story mockStoryTwo = TestUtil.createMockStory();
//
//        final ArrayList<Story> stories = new ArrayList<>();
//        stories.add(mockStoryOne);
//        stories.add(mockStoryTwo);
//
//
//        RecyclerView leftDrawer = (RecyclerView) activity.findViewById(R.id.rvStoryContainer);
//        assertThat(leftDrawer).isNotNull();
//        leftDrawer.measure(0,0);
//        leftDrawer.layout(0,0,100,1000);
//        StoriesListAdapter storiesListAdapter =  new StoriesListAdapter(activity.getApplicationContext(),stories);
//        leftDrawer.setAdapter(storiesListAdapter);
//        activity.setStories(stories);
//        activity.sortStories();
//
//        boolean expected = activity.getStories().size()>1&& (activity.getStories().get(0).time >= activity.getStories().get(1).time);
//
//        Assert.assertEquals(true, expected);
//
//    }
//
//
//    @Test
//    public void testParcelStories() throws Exception {
//
//        Story mockStoryOne = TestUtil.createMockStory();
//        Parcel parcel = Parcel.obtain();
//        mockStoryOne.writeToParcel(parcel, mockStoryOne.describeContents());
//        parcel.setDataPosition(0);
//
//        Story createdFromParcel = Story.CREATOR.createFromParcel(parcel);
//
//        Assert.assertEquals(createdFromParcel, mockStoryOne);
//
//    }
//
//
//    @Test
//    public void testStoryModelUtilities() throws Exception {
//
//        Story mockStoryOne = TestUtil.createMockStory();
//        Story mockStoryClone = TestUtil.createMockStoryWithStory(mockStoryOne);
//
//        Assert.assertTrue(mockStoryOne.equals(mockStoryClone) && mockStoryClone.equals(mockStoryOne));
//        Assert.assertTrue(mockStoryOne.hashCode() == mockStoryClone.hashCode());
//
//    }


    @After
    public void tearDown() {
        activity.finish();
        activity = null ;
    }

}
