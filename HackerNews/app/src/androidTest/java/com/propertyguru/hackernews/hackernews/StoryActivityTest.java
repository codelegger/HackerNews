//package com.propertyguru.hackernews.hackernews;
//
///**
// * Created by uba on 28/06/2017.
// */
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.propertyguru.hackernews.hackernews.data.model.Story;
//import com.propertyguru.hackernews.hackernews.ui.activities.StoryActivity;
//import com.propertyguru.hackernews.hackernews.utils.TestUtil;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import rx.Observable;
//
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(AndroidJUnit4.class)
//public class StoryActivityTest {
//
//
//    TestRule<StoryActivity> main =
//            new ActivityTestRule<>(StoryActivity.class, false, false);
//
//    @Rule
//    public final TestComponentRule component = new TestComponentRule();
//    @Test
//    public void testPostsShowAndAreScrollableInFeed() {
//        List<Long> postIdList = TestUtil.createMockPostIdList(10);
//        List<Story> postList = new ArrayList<>();
//        for (Long id : postIdList) {
//            postList.add(TestUtil.createMockStoryWithId(id));
//        }
//
//        stubMockPosts(postIdList, postList);
//        main.launchActivity(null);
//
//        checkPostsDisplayOnRecyclerView(postList);
//    }
//
//
//    private void stubMockPosts(List<Long> postIds, List<Story> mockPosts) {
//        when(component.getMockHackerNewsService().getTopStories())
//                .thenReturn(Observable.just(postIds));
//        for (Long id : postIds) {
//            for (Story post : mockPosts) {
//                if (post.id.equals(id)) {
//                    when(component.getMockHackerNewsService().getStoryItem(id.toString()))
//                            .thenReturn(Observable.just(post));
//                }
//            }
//        }
//    }
//}
