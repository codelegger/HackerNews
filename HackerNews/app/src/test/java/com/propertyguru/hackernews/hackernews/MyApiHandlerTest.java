package com.propertyguru.hackernews.hackernews;

import com.propertyguru.hackernews.hackernews.data.manager.ApiHandler;
import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.data.server.FeedService;
import com.propertyguru.hackernews.hackernews.utils.TestUtil;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.*;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by uba on 11/06/2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class MyApiHandlerTest {
    final static  int commentDepth = 0;
    private ApiHandler mockDataManager;
    private FeedService mockFeederService;
    private Story story;

    @Before
    public void setUp() {
        mockFeederService = mock(FeedService.class);

        mockDataManager = new ApiHandler(mockFeederService,
                Schedulers.immediate());
    }


    @Test
    public void testTopStories() throws Exception {


        Story mockStoryOne = TestUtil.createMockStory();
        Story mockStoryTwo = TestUtil.createMockStory();
        Story mockStoryThree = TestUtil.createMockStory();
        Story mockStoryFour = TestUtil.createMockStory();

        when(mockFeederService.getStoryItem(String.valueOf(mockStoryOne.id)))
                .thenReturn(Observable.just(mockStoryOne));
        when(mockFeederService.getStoryItem(String.valueOf(mockStoryTwo.id)))
                .thenReturn(Observable.just(mockStoryTwo));
        when(mockFeederService.getStoryItem(String.valueOf(mockStoryThree.id)))
                .thenReturn(Observable.just(mockStoryThree));
        when(mockFeederService.getStoryItem(String.valueOf(mockStoryFour.id)))
                .thenReturn(Observable.just(mockStoryFour));

        final List<Long> storyIds = new ArrayList<>();
        storyIds.add(mockStoryOne.id);
        storyIds.add(mockStoryTwo.id);
        storyIds.add(mockStoryThree.id);
        storyIds.add(mockStoryFour.id);

        final List<Story> stories = new ArrayList<>();

        mockDataManager.getPostsFromIds(storyIds).subscribe(new Action1<Story>() {
            @Override
            public void call(Story story) {
                stories.add(story);
            }
        });

        Assert.assertEquals(4, stories.size());
        Assert.assertTrue(stories.contains(mockStoryOne));
        Assert.assertTrue(stories.contains(mockStoryTwo));
        Assert.assertTrue(stories.contains(mockStoryThree));
        Assert.assertTrue(stories.contains(mockStoryFour));

    }
    @Test
    public void testComments() throws Exception {

        Story mockStoryOne = TestUtil.createMockStory();
        Comment mockComment = TestUtil.createMockComment();
        mockStoryOne.kids.add(mockComment.id);
        final ArrayList<Comment> commentsRs = new ArrayList<>();

        when(mockFeederService.getCommentItem(String.valueOf(mockStoryOne.kids.get(0))))
                .thenReturn(Observable.just(mockComment));


            mockDataManager.getPostComments(mockStoryOne.kids,commentDepth).subscribe(new Action1<Comment>() {
                @Override
                public void call(Comment comment) {
                    commentsRs.add(comment);
                }
            });



        Assert.assertEquals(1, commentsRs.size());
        Assert.assertTrue(commentsRs.contains(mockComment));
    }



    @Test
    public void test1()
    {

    }

}
