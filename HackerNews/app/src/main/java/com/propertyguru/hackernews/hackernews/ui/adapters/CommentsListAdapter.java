package com.propertyguru.hackernews.hackernews.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.propertyguru.hackernews.hackernews.R;
import com.propertyguru.hackernews.hackernews.data.model.ChildComment;
import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.ui.HackerNewsApplication;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Uba
 */

public class CommentsListAdapter extends ExpandableRecyclerAdapter<Comment, ChildComment, CommentsListAdapter.CommentParentViewHolder, CommentsListAdapter.CommentChildViewHolder> {

    private LayoutInflater mInflater;
    private List<Comment> mCommentsList;

    public CommentsListAdapter(Context context, @NonNull List<Comment> items) {
        super(items);
        mCommentsList = items;
        mInflater = LayoutInflater.from(context);
    }

//    public int getItemCount() {
//        return mCommentsList.size();
//    }

    @NonNull
    @Override
    public CommentParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View parentView = mInflater.inflate(R.layout.comment_parent_view, parentViewGroup, false);
        return new CommentParentViewHolder(parentView);
    }

    @NonNull
    @Override
    public CommentChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View childView = mInflater.inflate(R.layout.comment_child_layout, childViewGroup, false);
        return new CommentChildViewHolder(childView);
    }


    @Override
    public void onBindParentViewHolder(@NonNull CommentParentViewHolder parentViewHolder, int parentPosition, @NonNull Comment parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull CommentChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ChildComment child) {
        childViewHolder.bind(child);
    }

    public class CommentParentViewHolder extends ParentViewHolder {
        private TextView tvCommentTitle, tvCommentDetail, tvCommentTapForMore;
        private Comment mItem;

        public CommentParentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentTitle = (TextView) itemView.findViewById(R.id.tvCommentTitle);
            tvCommentDetail = (TextView) itemView.findViewById(R.id.tvCommentDetail);
            tvCommentTapForMore = (TextView) itemView.findViewById(R.id.tvCommentTapForMore);
        }

        public void bind(@NonNull Comment item) {
            if (item != null) {
                tvCommentTitle.setText(item.by);
                tvCommentDetail.setText(Html.fromHtml(item.text));
                mItem = item;
                if (mItem != null && mItem.kids.size() > 0) {
                    tvCommentTapForMore.setText(HackerNewsApplication.getsInstance().getString(R.string.tap_for_hide_comments));
                } else {
                    tvCommentTapForMore.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (!expanded) {
                tvCommentTapForMore.setText(HackerNewsApplication.getsInstance().getString(R.string.tap_for_hide_comments));
            } else {
                if (mItem != null && mItem.kids.size() > 0) {
                    tvCommentTapForMore.setText(HackerNewsApplication.getsInstance().getString(R.string.tap_for_more_comments));
                } else {
                    tvCommentTapForMore.setVisibility(View.GONE);
                }
            }
        }
    }

    public class CommentChildViewHolder extends ChildViewHolder {
        private TextView tvCommentTitle, tvCommentDetail;

        public CommentChildViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentTitle = (TextView) itemView.findViewById(R.id.tvCommentTitle);
            tvCommentDetail = (TextView) itemView.findViewById(R.id.tvCommentDetail);

        }

        public void bind(@NonNull ChildComment item) {
            if (item != null) {
                if (!TextUtils.isEmpty(item.text)) {
                    tvCommentTitle.setText(item.by);
                    tvCommentDetail.setText(Html.fromHtml(item.text));
                }
            }
        }
    }

    public void addItemInList(Comment comment) {
        mCommentsList.add(comment);
        notifyParentInserted(mCommentsList.size() - 1);
    }
}
