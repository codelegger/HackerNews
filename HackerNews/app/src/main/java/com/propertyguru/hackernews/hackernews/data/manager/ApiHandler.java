package com.propertyguru.hackernews.hackernews.data.manager;

import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.data.server.FeedService;
import com.propertyguru.hackernews.hackernews.data.server.RetroFitManager;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by uba on 09/06/2017.
 */

public class ApiHandler {

    private FeedService feedService;

    private Scheduler subscribeScheduler;

    public ApiHandler() {
        this.feedService = new RetroFitManager().fsInstance();//// TODO: 09/06/2017  its a bad approach to instanitte it in constructor either use
        this.subscribeScheduler = Schedulers.io();
    }

    public ApiHandler(FeedService service , Scheduler scheduler)
    {
        this.feedService =service ;
        this.subscribeScheduler = scheduler;
    }


    public Scheduler getScheduler() {
        return subscribeScheduler;
    }

    public Observable<Story> getTopStories() {
        return feedService.getTopStories()
                .concatMap(new Func1<List<Long>, Observable<? extends Story>>() {
                    @Override
                    public Observable<? extends Story> call(List<Long> longs) {
                        return getPostsFromIds(longs);
                    }
                });
    }


    public Observable<Story> getPostsFromIds(List<Long> storyIds) {
        return Observable.from(storyIds)
                .concatMap(new Func1<Long, Observable<Story>>() {
                    @Override
                    public Observable<Story> call(Long aLong) {
                        return feedService.getStoryItem(String.valueOf(aLong));
                    }
                }).flatMap(new Func1<Story, Observable<Story>>() {
                    @Override
                    public Observable<Story> call(Story post) {
                        return post.title != null ? Observable.just(post) : Observable.<Story>empty();
                    }
                });
    }

    public Observable<Comment> getPostComments(final List<Long> commentIds, final int depth) {
        return Observable.from(commentIds)
                .concatMap(new Func1<Long, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(Long aLong) {
                        return feedService.getCommentItem(String.valueOf(aLong));
                    }
                }).concatMap(new Func1<Comment, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(Comment comment) {
                        comment.depth = depth;
                        if (comment.kids == null || comment.kids.isEmpty() || comment.depth >= 1) {
                            return Observable.just(comment);
                        } else {
                            return Observable.just(comment)
                                    .mergeWith(getPostComments(comment.kids, depth + 1));
                        }
                    }
                }).filter(new Func1<Comment, Boolean>() {
                    @Override
                    public Boolean call(Comment comment) {
                        return (comment.by != null && !comment.by.trim().isEmpty()
                                && comment.text != null && !comment.text.trim().isEmpty());
                    }
                });
    }
}
