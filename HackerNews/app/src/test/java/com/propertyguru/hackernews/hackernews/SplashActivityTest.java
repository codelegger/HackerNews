package com.propertyguru.hackernews.hackernews;

import android.app.Activity;
import android.content.Intent;

import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.ui.activities.SplashScreenActivity;
import com.propertyguru.hackernews.hackernews.ui.activities.StoryActivity;
import com.propertyguru.hackernews.hackernews.utils.TestUtil;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import  org.robolectric.Shadows;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static org.mockito.Mockito.when;

/**
 * Created by uba on 11/06/2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class SplashActivityTest {


    private SplashScreenActivity mySplasActivity;
    @Before
    public void setUp() {

        //controller = Robolectric.buildActivity(SplashScreenActivity.class).create();
       // mySplasActivity = controller.get();
        mySplasActivity = Robolectric.buildActivity(SplashScreenActivity.class).create().resume().get();
    }


    @Test
    public void testStoryActivityStart() throws Exception {

        Intent intent = new Intent(mySplasActivity, StoryActivity.class);
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        Assert.assertEquals(Shadows.shadowOf(mySplasActivity).peekNextStartedActivity().getComponent().getClassName(),intent.getComponent().getClassName());

    }

    @After
    public void tearDown() {
        mySplasActivity.finish();
    }
}
