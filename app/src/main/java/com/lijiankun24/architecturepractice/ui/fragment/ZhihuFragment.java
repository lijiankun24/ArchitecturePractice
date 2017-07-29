package com.lijiankun24.architecturepractice.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijiankun24.architecturepractice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuFragment extends Fragment {


    public ZhihuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zhihu, container, false);
    }

}
