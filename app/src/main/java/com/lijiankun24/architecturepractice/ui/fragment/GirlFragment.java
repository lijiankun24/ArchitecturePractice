package com.lijiankun24.architecturepractice.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.utils.Consts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * GirlFragment.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlFragment extends LifecycleFragment {

    public static final String TAG = "GirlFragment";

    private static final String GIRL_IMG_URL = "girl_img_url";

    private View mStatusBar = null;

    private Toolbar mToolbar = null;

    private Boolean mIsHidden = false;

    private PhotoViewAttacher mPhotoViewAttacher;

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
        initView(view);
        return view;
    }

    private void initView(View view) {
        mStatusBar = view.findViewById(R.id.fake_status_bar);
        mStatusBar.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.black));

        PhotoView photoView = view.findViewById(R.id.photoView);
        mPhotoViewAttacher = new PhotoViewAttacher(photoView);
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                toggleToolbar();
            }
        });
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.girl_save))
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                                saveImage();
                            }
                        }).show();
                return true;
            }
        });
        Glide.with(this)
                .load(mGirlImgUrl)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView);

        mToolbar = view.findViewById(R.id.toolbar);
    }

    private void saveImage() {
        File directory = new File(Environment.getExternalStorageDirectory(), Consts.FILEROOTPATH);
        if (!directory.exists())
            directory.mkdirs();
        Bitmap drawingCache = mPhotoViewAttacher.getImageView().getDrawingCache();
        try {
            File file = new File(directory, new Date().getTime() + Consts.IMAGE_FORMAT);
            FileOutputStream fos = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getContext().getApplicationContext().sendBroadcast(intent);
            showSnackbar(R.string.girl_save_succeed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showSnackbar(R.string.girl_save_failed);
        }
    }

    private void showSnackbar(int msgRes) {
        String msg = getString(msgRes);
        if (!isAdded() && getActivity() == null && TextUtils.isEmpty(msg)) {
            return;
        }
        Snackbar.make(getActivity().getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void toggleToolbar() {
        mToolbar.animate()
                .translationY(mIsHidden ? 0 : -(mToolbar.getHeight() + mStatusBar.getHeight()))
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }
}
