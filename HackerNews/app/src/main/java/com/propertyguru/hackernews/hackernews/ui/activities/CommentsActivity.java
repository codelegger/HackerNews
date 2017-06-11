package com.propertyguru.hackernews.hackernews.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.propertyguru.hackernews.hackernews.R;
import com.propertyguru.hackernews.hackernews.data.manager.ApiHandler;
import com.propertyguru.hackernews.hackernews.data.model.ChildComment;
import com.propertyguru.hackernews.hackernews.data.model.Comment;
import com.propertyguru.hackernews.hackernews.data.model.Story;
import com.propertyguru.hackernews.hackernews.interfaces.IDialogCallback;
import com.propertyguru.hackernews.hackernews.ui.adapters.CommentsListAdapter;
import com.propertyguru.hackernews.hackernews.utils.AppConstants;
import com.propertyguru.hackernews.hackernews.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/*
 * Created by Uba on 6/7/17.
 */

public class CommentsActivity extends AppCompatActivity {
    private List<Subscription> mSubscriptions = new ArrayList<>();
    private ArrayList<Comment> mComments = new ArrayList<>();
    private Story mMainStory;

    private CommentsListAdapter mCommentsListAdapter;

    //UI elements
    private TextView tvTittle;
    private TextView tvPostBy;
    private TextView tvTotalComments;
    private RelativeLayout pbLoadingLayout;
    private RecyclerView rvComments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //extract UI elements from Xml
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments.setLayoutManager(linearLayoutManager);

        pbLoadingLayout = (RelativeLayout) findViewById(R.id.pbLoadingLayout);
        tvTotalComments = (TextView) findViewById(R.id.tvTotalComments);
        tvPostBy = (TextView) findViewById(R.id.tvPostBy);
        tvTittle = (TextView) findViewById(R.id.tvTittle);

        if (getIntent() != null && getIntent().hasExtra(AppConstants.EXTRA_ITEM)) {
            mMainStory = getIntent().getParcelableExtra(AppConstants.EXTRA_ITEM);
            init();
        } else {
            AppUtils.showToast("Data Error");
            finish();
        }
    }

    private void init() {
        //Show Loading at start
        showLoadingUiElements();

        //Show Back Button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(mMainStory.title);
        }

        tvTittle.setText(mMainStory.title);
        tvPostBy.setText("Posted by " + mMainStory.by);

        if (mMainStory.kids != null && !mMainStory.kids.isEmpty()) {

            tvTotalComments.setText(mMainStory.kids.size() + " Comments");
//
//            mCommentsListAdapter = new CommentsListAdapter(this, mComments);
//            rvComments.setAdapter(mCommentsListAdapter);

            fetchComments(mMainStory.kids);
        }

    }

    private void hideLoadingUiElements() {
        if (pbLoadingLayout != null && pbLoadingLayout.getVisibility() == View.VISIBLE) {
            pbLoadingLayout.setVisibility(View.GONE);
        }
    }

    private void showLoadingUiElements() {
        if (pbLoadingLayout != null) {
            pbLoadingLayout.setVisibility(View.VISIBLE);
        }
    }

    private void fetchComments(List<Long> commentIds) {
        //Show Error Message if Network is not Available
        if (!AppUtils.isNetworkConnected()) {
            showNoNetworkDialog();
            hideLoadingUiElements();
            return;
        }
        ApiHandler _dmInstance = new ApiHandler();
        if (commentIds != null && !commentIds.isEmpty()) {
            mSubscriptions.add(_dmInstance.getPostComments(commentIds, 0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(_dmInstance.getScheduler())
                    .subscribe(new Subscriber<Comment>() {

                        @Override
                        public void onError(Throwable e) {
                            if (!AppUtils.isNetworkConnected()) {
                                showNoNetworkDialog();
                                hideLoadingUiElements();
                                return;
                            }
                        }

                        @Override
                        public void onNext(Comment comment) {
                            hideLoadingUiElements();

                            if (comment.depth == 0) {
                                mComments.add(comment);
                            }

                            if (comment.depth == 0 && comment.kids.size() != comment.comments.size()) {
                                return;
                            }

                            if (comment.depth > 0) {
                                Comment lastComment = mComments.get(mComments.size() - 1);
                                lastComment.comments.add(ChildComment.cloneChild(comment));
                                if (lastComment.kids.size() != lastComment.comments.size()) {
                                    return;
                                }
                            }

                            if (mCommentsListAdapter == null) {
                                ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
                                commentArrayList.addAll(mComments);
                                mCommentsListAdapter = new CommentsListAdapter(CommentsActivity.this, commentArrayList);
                                rvComments.setAdapter(mCommentsListAdapter);
                            } else {
                                mCommentsListAdapter.addItemInList(mComments.get(mComments.size() - 1));
                            }

                        }

                        @Override
                        public void onCompleted() {
                        }

                    }));
        } else {
            hideLoadingUiElements();
        }
    }

    private void showNoNetworkDialog() {
        AppUtils.showAlertMessage(CommentsActivity.this,
                CommentsActivity.this.getString(R.string.error_string),
                CommentsActivity.this.getString(R.string.error_msg),
                CommentsActivity.this.getString(R.string.go_back), new IDialogCallback() {
                    @Override
                    public void onPositiveClick(Object o) {
                        CommentsActivity.this.finish();
                    }

                    @Override
                    public void onNegativeClick(Object o) {
                        //Do Nothing
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
