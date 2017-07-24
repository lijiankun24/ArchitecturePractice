package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lijiankun24.architecturepractice.R;

import uk.co.senab.photoview.PhotoView;

/**
 * GirlFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlFragment extends LifecycleFragment {

    public static final String TAG = "GirlFragment";

    private static final String GIRL_IMG_URL = "girl_img_url";

    private String mGirlImgUrl = null;

    public GirlFragment() {
    }

    public static GirlFragment newInstance(String girlImgUrl) {
        GirlFragment fragment = new GirlFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GIRL_IMG_URL, girlImgUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mGirlImgUrl = bundle.getString(GIRL_IMG_URL, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        Glide.with(this)
                .load(mGirlImgUrl)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView);
        return view;
    }
}
