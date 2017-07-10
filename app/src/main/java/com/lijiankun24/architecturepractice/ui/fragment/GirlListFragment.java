package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.Injection;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.activity.MainActivity;
import com.lijiankun24.architecturepractice.ui.adapter.GirlListAdapter;
import com.lijiankun24.architecturepractice.ui.listener.OnGirlClickListener;
import com.lijiankun24.architecturepractice.utils.L;
import com.lijiankun24.architecturepractice.viewmodel.GirlListViewModel;

import java.util.List;

/**
 * GirlListFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class GirlListFragment extends LifecycleFragment {

    public static final String TAG = "GirlListFragment";

    private GirlListViewModel mGirlListViewModel = null;

    private GirlListAdapter mGirlListAdapter = null;

    private final OnGirlClickListener mGirlClickListener = new OnGirlClickListener() {
        @Override
        public void onClick(Girl girl) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showGirl(girl);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_list, container, false);
        initGirlList(((RecyclerView) view.findViewById(R.id.rv_girl_list)));
        initSwipeRefreshLayout(((SwipeRefreshLayout) view.findViewById(R.id.srl)));
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
                .Factory(getActivity().getApplication(), Injection.getGirlsDataRepository());
        mGirlListViewModel = ViewModelProviders.of(this, factory)
                .get(GirlListViewModel.class);
        mGirlListViewModel.getLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(@Nullable List<Girl> girls) {
                if (girls == null || girls.size() == 0) {
                    return;
                }
                L.i("girls size " + girls.size());
                mGirlListAdapter.setGirlList(girls);
            }
        });
    }

    private void initGirlList(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mGirlListAdapter = new GirlListAdapter(mGirlClickListener);
        recyclerView.setAdapter(mGirlListAdapter);
    }

    private void initSwipeRefreshLayout(final SwipeRefreshLayout swipeRefresh) {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGirlListViewModel.refreshGrilsData();
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}
