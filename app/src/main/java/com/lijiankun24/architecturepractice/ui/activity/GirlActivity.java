package com.lijiankun24.architecturepractice.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.utils.Consts;
import com.lijiankun24.architecturepractice.utils.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlActivity extends BaseActivity {

    private static final String GIRL_IMG_URL = "girl_img_url";

    private PhotoViewAttacher mPhotoViewAttacher;

    private Boolean mIsToolbarHidden = false;

    private String mGirlImgUrl = null;

    private Toolbar mToolbar = null;

    private View mRLGirlRoot = null;

    private View mStatusBar = null;

    public static void startGirlActivity(Activity activity, String girlUrl) {
        if (activity == null || TextUtils.isEmpty(girlUrl)) {
            return;
        }
        Intent intent = new Intent(activity, GirlActivity.class);
        intent.putExtra(GIRL_IMG_URL, girlUrl);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        readIntent();
        initView();
    }

    private void readIntent() {
        Intent intent = GirlActivity.this.getIntent();
        if (intent.hasExtra(GIRL_IMG_URL)) {
            mGirlImgUrl = intent.getStringExtra(GIRL_IMG_URL);
        }
    }

    private void initView() {

        PhotoView photoView = (PhotoView)findViewById(R.id.photoView);
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
                showSaveGirlDialog();
                return true;
            }
        });
        Glide.with(this)
                .load(mGirlImgUrl)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar, true, R.string.girl_title);

        mRLGirlRoot = findViewById(R.id.rl_girl_root);
        mStatusBar = findViewById(R.id.fake_status_bar);
    }

    private void showSaveGirlDialog() {
        new AlertDialog.Builder(GirlActivity.this)
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
                        saveGirl();
                    }
                }).show();
    }

    private void saveGirl() {
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
            GirlActivity.this.getApplicationContext().sendBroadcast(intent);
            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_succeed));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_failed));
        }
    }

    private void toggleToolbar() {
        mToolbar.animate()
                .translationY(mIsToolbarHidden ? 0 : -(mToolbar.getHeight() + mStatusBar.getHeight()))
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolbarHidden = !mIsToolbarHidden;
    }
}
