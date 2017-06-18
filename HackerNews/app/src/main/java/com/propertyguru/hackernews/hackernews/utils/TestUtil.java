package com.propertyguru.hackernews.hackernews.utils;

import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by uba on 11/06/2017.
 */

public class TestUtil {

    public static Story createMockStory() {
        Story story = new Story();
        story.id = new Random().nextLong();
        story.url = "http://www.propertyguru.com.sg";
        story.title = "Mock Story" + story.id;
        story.score = 9999L;
        story.by = "XXXXX";
        story.time = new Random().nextLong();
        story.kids = new ArrayList<>();
        return story;
    }

    public static Story createMockStoryWithStory(Story mockIt) {
        Story story = new Story();
        story.id = mockIt.id;
        story.url = mockIt.url;
        story.title = mockIt.title;
        story.score = mockIt.score;
        story.by = mockIt.by;
        story.time = mockIt.time;
        story.kids = new ArrayList<>();
        return story;
    }
    public static Comment createMockComment(){
        Comment mockComment = new Comment();

        mockComment.text = "This is a test parent comment";
        mockComment.time = 99999L;
        mockComment.by = "DummyUser";
        mockComment.id = new Random().nextLong();
        mockComment.type = "D_type";
        mockComment.depth = 0;
        mockComment.isTopLevelComment = true;

        return mockComment;
    }



}
