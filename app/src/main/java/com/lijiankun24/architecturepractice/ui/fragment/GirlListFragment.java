package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.activity.GirlActivity;
import com.lijiankun24.architecturepractice.ui.adapter.GirlListAdapter;
import com.lijiankun24.architecturepractice.ui.listener.OnItemClickListener;
import com.lijiankun24.architecturepractice.utils.L;
import com.lijiankun24.architecturepractice.utils.Util;
import com.lijiankun24.architecturepractice.viewmodel.GirlListViewModel;

import java.util.List;

/**
 * GirlListFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class GirlListFragment extends Fragment{

    private GirlListViewModel mGirlListViewModel = null;

    private GirlListAdapter mGirlListAdapter = null;

    private SwipeRefreshLayout mRefreshLayout = null;

    private ProgressBar mLoadMorebar = null;

    private View RLGirlRoot = null;

    private final OnItemClickListener<Girl> mGirlClickListener = new OnItemClickListener<Girl>() {
        @Override
        public void onClick(Girl girl) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                if (Util.isNetworkConnected(MyApplication.getInstance())) {
                    GirlActivity.startGirlActivity(getActivity(), girl.getUrl());
                } else {
                    Util.showSnackbar(RLGirlRoot, getString(R.string.network_error));
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_list, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    private void subscribeUI() {
        if (!isAdded()) {
            return;
        }
        GirlListViewModel.Factory factory = new GirlListViewModel
                .Factory(MyApplication.getInstance(),
                Injection.getDataRepository(MyApplication.getInstance()));
        mGirlListViewModel = ViewModelProviders.of(this, factory).get(GirlListViewModel.class);
        mGirlListViewModel.getGilrsLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(@Nullable List<Girl> girls) {
                if (girls == null || girls.size() == 0) {
                    return;
                }
                L.i("girls size " + girls.size());
                mGirlListAdapter.setGirlList(girls);
            }
        });
        mGirlListViewModel.getLoadMoreState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean state) {
                if (state == null) {
                    return;
                }
                L.i("state " + state);
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    mLoadMorebar.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
        mGirlListViewModel.refreshGrilsData();
        mRefreshLayout.setRefreshing(true);
    }

    private void initView(View view) {
        Context context = view.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_girl_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        mGirlListAdapter = new GirlListAdapter(mGirlClickListener);
        recyclerView.setAdapter(mGirlListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                int lastPosition = layoutManager
                        .findLastVisibleItemPosition();
                if (lastPosition == mGirlListAdapter.getItemCount() - 1) {
                    // 上拉加载更多数据
                    mGirlListViewModel.loadNextPageGirls();
                }
            }
        });

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGirlListAdapter.clearGirlList();
                mRefreshLayout.setRefreshing(true);
                mGirlListViewModel.refreshGrilsData();
            }
        });
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mLoadMorebar = (ProgressBar)view.findViewById(R.id.load_more_bar);
        RLGirlRoot = view.findViewById(R.id.rl_girl_root);
    }
}
