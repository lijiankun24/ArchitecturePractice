package com.lijiankun24.architecturepractice.ui.fragment;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lijiankun24.architecturepractice.MyApplication;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.Injection;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;
import com.lijiankun24.architecturepractice.ui.adapter.ZhihuListAdapter;
import com.lijiankun24.architecturepractice.utils.L;
import com.lijiankun24.architecturepractice.viewmodel.ZhihuListViewModel;

import java.util.List;

/**
 * ZhihuListFragment.java
 * <p>
 * Created by lijiankun on 17/7/30.
 */

public class ZhihuListFragment extends LifecycleFragment {

    private ZhihuListViewModel mListViewModel = null;

    private SwipeRefreshLayout mRefreshLayout = null;

    private ZhihuListAdapter mAdapter = null;

    private ProgressBar mLoadMorebar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_list, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    private void subscribeUI() {
        ZhihuListViewModel.Factory factory = new ZhihuListViewModel
                .Factory(MyApplication.getInstance()
                , Injection.getGirlsDataRepository(MyApplication.getInstance()));
        mListViewModel = ViewModelProviders.of(this, factory).get(ZhihuListViewModel.class);
        mListViewModel.getZhihuList().observe(this, new Observer<List<ZhihuStory>>() {
            @Override
            public void onChanged(@Nullable List<ZhihuStory> stories) {
                if (stories == null || stories.size() <= 0) {
                    return;
                }
                L.i("size is " + stories.size());
                mAdapter.setStoryList(stories);
            }
        });
        mListViewModel.isLoadingZhihuList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) {
                    return;
                }
                L.i("state " + aBoolean);
                mRefreshLayout.setRefreshing(false);
                mLoadMorebar.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE);
            }
        });
        mListViewModel.refreshZhihusData();
    }

    private void initView(View view) {
        if (view == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ZhihuListAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rv_zhihu_list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ZhihuOnScrollListener());

        mRefreshLayout = view.findViewById(R.id.srl_zhihu);
        mRefreshLayout.setOnRefreshListener(new ZhihuSwipeListener());
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mLoadMorebar = view.findViewById(R.id.bar_load_more_zhihu);
    }

    private class ZhihuSwipeListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mAdapter.clearStoryList();
            mListViewModel.refreshZhihusData();
        }
    }

    private class ZhihuOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
            int lastPosition = layoutManager
                    .findLastCompletelyVisibleItemPosition();
            if (lastPosition == mAdapter.getItemCount() - 1) {
                // 上拉加载更多数据
                mListViewModel.loadNextPageZhihu();
            }
        }
    }
}
