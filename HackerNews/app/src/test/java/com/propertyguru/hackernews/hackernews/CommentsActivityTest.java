package com.propertyguru.hackernews.hackernews;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.propertyguru.hackernews.hackernews.data.manager.ApiHandler;
import com.propertyguru.hackernews.hackernews.data.model.ChildComment;
import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.data.server.FeedService;
import com.propertyguru.hackernews.hackernews.ui.activities.CommentsActivity;
import com.propertyguru.hackernews.hackernews.ui.activities.StoryActivity;
import com.propertyguru.hackernews.hackernews.ui.adapters.CommentsListAdapter;
import com.propertyguru.hackernews.hackernews.ui.adapters.StoriesListAdapter;
import com.propertyguru.hackernews.hackernews.utils.AppConstants;
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
import org.robolectric.shadows.ShadowLooper;

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
public class CommentsActivityTest {
    final static  int commentDepth = 0;
    private ApiHandler mockDataManager;
    private FeedService mockFeederService;

    private CommentsActivity activity;
    Story mockStoryOne;
    Comment mockComment;
    @Before
    public void setUp() {
         mockStoryOne = TestUtil.createMockStory();
         mockComment = TestUtil.createMockComment();
        mockStoryOne.kids.add(mockComment.id);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(AppConstants.EXTRA_ITEM, mockStoryOne);
         activity = Robolectric.buildActivity(CommentsActivity.class,intent).create().get();

    }


    @Test
    public void testCommentsActivity() throws Exception {
        Assert.assertEquals(mockStoryOne.title, activity.getSupportActionBar().getTitle().toString());
    }

    @Test
    public void testModelUtilities() throws Exception {

        Comment mCmt1 = TestUtil.createMockComment();
        Comment mCmt2 = TestUtil.createCloneComment(mCmt1);

        ChildComment cloneComment = ChildComment.cloneChild(mCmt1);

        Assert.assertTrue(mCmt1.equals(mCmt2) && mCmt2.equals(mCmt1));
        Assert.assertTrue(mCmt1.id==cloneComment.id);

        Assert.assertTrue(mCmt1.hashCode() == mCmt2.hashCode());

    }

    @Test
    public void testCommentChildModelUtilities() throws Exception {

        ChildComment mCmt1 = TestUtil.createMockChildComment();
        ChildComment mCmt2 = TestUtil.createCloneChildComment(mCmt1);



        Assert.assertTrue(mCmt1.equals(mCmt2) && mCmt2.equals(mCmt1));
        Assert.assertTrue(mCmt1.hashCode() == mCmt2.hashCode());

    }


    @Test
    public void testCommentActivityStart() throws Exception {

        ArrayList<Comment> commentArrayList = new ArrayList<Comment>();

        commentArrayList.add(TestUtil.createMockComment());
        commentArrayList.add(TestUtil.createMockComment());
        commentArrayList.add(TestUtil.createMockComment());


        Comment mockCommentt =  TestUtil.createMockComment();
        RecyclerView leftDrawer = (RecyclerView) activity.findViewById(R.id.rvComments);

        assertThat(leftDrawer).isNotNull();

        leftDrawer.measure(0,0);
        leftDrawer.layout(0,0,100,1000);

        CommentsListAdapter commentsListAdapter =  new CommentsListAdapter(activity.getApplicationContext(),commentArrayList);
        leftDrawer.setAdapter(commentsListAdapter);
        commentsListAdapter.addItemInList(mockCommentt);


        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        RecyclerView.ViewHolder holder = leftDrawer.findViewHolderForAdapterPosition(0);
        //TextView tvCommentTapForMore = (TextView) holder.itemView.findViewById(R.id.tvCommentTapForMore);

        Assert.assertEquals(commentArrayList.size(),commentsListAdapter.getParentList().size());


    }
//
//
//
//
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
    }

}

