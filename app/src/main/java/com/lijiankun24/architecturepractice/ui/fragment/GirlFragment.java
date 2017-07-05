package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijiankun24.architecturepractice.R;

/**
 * GirlFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlFragment extends LifecycleFragment {

    public static final String TAG = "GirlFragment";

    private static final String GIRL_ID = "girl_id";

    private String mGirlId = null;

    public GirlFragment() {
    }

    public static GirlFragment newInstance(String girlId) {
        GirlFragment fragment = new GirlFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GIRL_ID, girlId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        return view;
    }
}
