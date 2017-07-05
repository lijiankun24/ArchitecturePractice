package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.activity.MainActivity;
import com.lijiankun24.architecturepractice.ui.adapter.GirlListAdapter;
import com.lijiankun24.architecturepractice.ui.listener.OnGirlClickListener;
import com.lijiankun24.architecturepractice.viewmodel.GirlListViewModel;

import java.util.List;

/**
 * GirlListFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class GirlListFragment extends LifecycleFragment {

    public static final String TAG = "GirlListFragment";

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

        // init RecyclerView
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mGirlListAdapter = new GirlListAdapter(mGirlClickListener);
            recyclerView.setAdapter(mGirlListAdapter);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GirlListViewModel girlListViewModel = ViewModelProviders.of(this)
                .get(GirlListViewModel.class);
        subscribeUI(girlListViewModel);
    }

    private void subscribeUI(GirlListViewModel viewModel) {
        viewModel.getLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(@Nullable List<Girl> girls) {
                if (girls != null) {
                    mGirlListAdapter.setGirlList(girls);
                } else {

                }
            }
        });
    }
}
