package com.propertyguru.hackernews.hackernews.data.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uba on 09/06/2017.
 */

public class ChildComment {

    public String text;
    public Long time;
    public String by;
    public Long id;
    public String type;
    public ArrayList<Long> kids = new ArrayList<>();
    public ArrayList<ChildComment> comments;
    public int depth = 0;
    public boolean isTopLevelComment;

    public ChildComment() {
        comments = new ArrayList<>();
        isTopLevelComment = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildComment comment = (ChildComment) o;

        if (depth != comment.depth) return false;
        if (isTopLevelComment != comment.isTopLevelComment) return false;
        if (by != null ? !by.equals(comment.by) : comment.by != null) return false;
        if (comments != null ? !comments.equals(comment.comments) : comment.comments != null)
            return false;
        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (kids != null ? !kids.equals(comment.kids) : comment.kids != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (time != null ? !time.equals(comment.time) : comment.time != null) return false;
        if (type != null ? !type.equals(comment.type) : comment.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        final int seed = 22;
        result = seed * result + (time != null ? time.hashCode() : 0);
        result = seed * result + (by != null ? by.hashCode() : 0);
        result = seed * result + (id != null ? id.hashCode() : 0);
        result = seed * result + (type != null ? type.hashCode() : 0);
        result = seed * result + (kids != null ? kids.hashCode() : 0);
        result = seed * result + (comments != null ? comments.hashCode() : 0);
        result = seed * result + depth;
        result = seed * result + (isTopLevelComment ? 1 : 0);
        return result;
    }

    public static ChildComment cloneChild(Comment comment) {
        ChildComment childComment = new ChildComment();
        childComment.text = comment.text;
        childComment.type = comment.type;
        childComment.id = comment.id;
        childComment.depth = comment.depth;
        childComment.time = comment.time;
        childComment.by = comment.by;
        return childComment;
    }
}
