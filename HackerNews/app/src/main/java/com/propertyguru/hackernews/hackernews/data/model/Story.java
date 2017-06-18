package com.propertyguru.hackernews.hackernews.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by uba on 09/06/2017.
 */

public class Story implements Parcelable {

    public Long id;
    public String by;
    public Long time;
    public ArrayList<Long> kids;
    public String url;
    public Long score;
    public String title;
    public String text;

    private String string;

    public Story() { }



    @Override
    public boolean equals(Object objectToCheck) {

        if (objectToCheck == null || getClass() != objectToCheck.getClass())
        {
            return false;
        }
        if (this == objectToCheck)
        {
            return true;
        }

        Story story = (Story) objectToCheck;

        if(!TextUtils.isEmpty(by))
        {
            if(!this.by.equals(story.by))
            {
                return false;
            }
        }

        if(id!=null)
        {
            if(!this.id.equals(story.id))
            {
                return false;
            }
        }

        if(kids!=null)
        {
            if(!this.kids.equals(story.kids))
            {
                return false;
            }
        }

        if(score!=null)
        {
            if(!this.score.equals(story.score))
            {
                return false;
            }
        }

        if(time!=null)
        {
            if(!this.time.equals(story.time))
            {
                return false;
            }
        }

        if(!TextUtils.isEmpty(title))
        {
            if(!this.title.equals(story.title))
            {
                return false;
            }
        }

        if(!TextUtils.isEmpty(text))
        {
            if(!this.text.equals(story.text))
            {
                return false;
            }
        }

        if(!TextUtils.isEmpty(url))
        {
            if(!this.url.equals(story.url))
            {
                return false;
            }
        }


        return true;
    }

    @Override
    public int hashCode() {
        final int seed = 99 ;
        int result = by != null ? by.hashCode() : 0;
        result = seed * result + (id != null ? id.hashCode() : 0);
        result = seed * result + (time != null ? time.hashCode() : 0);
        result = seed * result + (kids != null ? kids.hashCode() : 0);
        result = seed * result + (url != null ? url.hashCode() : 0);
        result = seed * result + (score != null ? score.hashCode() : 0);
        result = seed * result + (title != null ? title.hashCode() : 0);
        result = seed * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.by);
        dest.writeValue(this.id);
        dest.writeValue(this.time);
        dest.writeSerializable(this.kids);
        dest.writeString(this.url);
        dest.writeValue(this.score);
        dest.writeString(this.title);
        dest.writeString(this.text);
//        dest.writeInt(this.postType == null ? -1 : this.postType.ordinal());
    }

    private Story(Parcel in) {
        this.by = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.time = (Long) in.readValue(Long.class.getClassLoader());
        this.kids = (ArrayList<Long>) in.readSerializable();//todo
        this.url = in.readString();
        this.score = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.text = in.readString();
        int tmpStoryType = in.readInt();
//        this.postType = tmpStoryType == -1 ? null : PostType.values()[tmpStoryType];
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
