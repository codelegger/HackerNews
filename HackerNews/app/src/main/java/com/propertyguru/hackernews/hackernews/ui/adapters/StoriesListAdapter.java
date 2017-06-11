package com.propertyguru.hackernews.hackernews.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propertyguru.hackernews.hackernews.R;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.ui.activities.CommentsActivity;
import com.propertyguru.hackernews.hackernews.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class StoriesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Story> mStories;
    private String TAG = StoriesListAdapter.class.getName();


    private Context mContext;
    private boolean isRefreshing = false;

    /*
    * @Para
    * */
    public StoriesListAdapter(Context context, List<Story> stories) {
        mStories = stories;
        this.mContext = context;
    }


    @Override
    public int getItemCount() {
        return mStories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new StoryViewHolder(inflater.inflate(R.layout.layout_story_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StoryViewHolder) holder).bindView(position);
    }


    public class StoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStoryTitle, tvDetails, tvCount;

        public StoryViewHolder(View itemView) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvStoryTitle = (TextView) itemView.findViewById(R.id.tvStoryTitle);
            tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
        }

        public void bindView(final int i) {
            if (i >= mStories.size()) {
                Log.i(TAG, "Index out of bound " + i);
            }
            final Story storyItem = mStories.get(i);
            if (storyItem != null) {
                tvCount.setText((i + 1) + AppConstants.EMPTY_STR);
                tvStoryTitle.setText(storyItem.title);
                String comments = AppConstants.EMPTY_STR;
                if (storyItem.kids != null) {
                    comments = AppConstants.PIPE_STR + storyItem.kids.size() +
                            AppConstants.SPACE_STR + AppConstants.COMMENTS_STR;
                }
                CharSequence time = DateUtils.getRelativeTimeSpanString(storyItem.time * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

                tvDetails.setText(storyItem.score + " points by " + storyItem.by + AppConstants.SPACE_STR + time + comments);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Launch Comments Activity
                        view.getContext().startActivity(getCommentActivityIntent(view.getContext(), storyItem));
                    }
                });
                itemView.setTag(storyItem);
            }
        }
    }

    public void updateDataSet(Story data) {
        if (mStories != null) {
            if (isRefreshing) {
                mStories.clear();
                isRefreshing = false;
            }
            mStories.add(data);
            notifyDataSetChanged();
        }
    }

    public void updateDataSet(ArrayList<Story> data) {
        mStories = data;
        isRefreshing = false;
        notifyDataSetChanged();
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setIsRefreshing(boolean pullToRefresh) {
        isRefreshing = pullToRefresh;
    }

    private Intent getCommentActivityIntent(Context context, Story story) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(AppConstants.EXTRA_ITEM, story);
        return intent;
    }
}
