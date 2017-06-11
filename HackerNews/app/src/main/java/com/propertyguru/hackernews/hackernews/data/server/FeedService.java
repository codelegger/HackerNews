package com.propertyguru.hackernews.hackernews.data.server;

import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by uba on 09/06/2017.
 */

public interface FeedService {

    String ENDPOINT = "https://hacker-news.firebaseio.com/v0/";

    /**
     * Return a list of the latest post IDs.
     */
    @GET("/topstories.json")
    Observable<List<Long>> getTopStories();


    /**
     * Return story item.
     */
    @GET("/item/{itemId}.json")
    Observable<Story> getStoryItem(@Path("itemId") String itemId);

    /**
     * Returns a comment item.
     */
    @GET("/item/{itemId}.json")
    Observable<Comment> getCommentItem(@Path("itemId") String itemId);

}
