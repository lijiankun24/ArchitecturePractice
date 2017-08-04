package com.lijiankun24.architecturepractice.ui.activity;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lijiankun24.architecturepractice.MyApplication;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.Injection;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;
import com.lijiankun24.architecturepractice.ui.widget.AppBarStateChangeListener;
import com.lijiankun24.architecturepractice.ui.widget.MarqueeText;
import com.lijiankun24.architecturepractice.utils.WebUtil;
import com.lijiankun24.architecturepractice.viewmodel.ZhihuViewModel;

public class ZhihuActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    public static final String ZHIHU_ID = "zhihu_id";

    public static final String ZHIHU_TITLE = "zhihu_title";

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private ZhihuViewModel mZhihuViewModel = null;

    private ImageView mIVZhihuHeader = null;

    private WebView mWebView = null;

    private String mZhihuTitle = null;

    private String mZhihuId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        readIntent();
        initView();
        subscribeUI();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    private void subscribeUI() {
        ZhihuViewModel.Factory factory = new ZhihuViewModel.Factory(MyApplication.getInstance(),
                Injection.getGirlsDataRepository(MyApplication.getInstance()), mZhihuId);
        mZhihuViewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel.class);
        mZhihuViewModel.getZhihuDetail().observe(this, new Observer<ZhihuStoryDetail>() {
            @Override
            public void onChanged(@Nullable ZhihuStoryDetail detail) {
                if (detail == null) {
                    return;
                }
                Glide.with(ZhihuActivity.this)
                        .load(detail.getImage())
                        .centerCrop()
                        .into(mIVZhihuHeader);
                if (TextUtils.isEmpty(detail.getBody())) {
                    mWebView.loadUrl(detail.getShare_url());
                } else {
                    String body = detail.getBody();
                    String[] cssList = detail.getCss();
                    String htmlTemp = WebUtil.buildHtmlWithCss(body, cssList, false);
                    mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, htmlTemp, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
                }
            }
        });
    }

    private void initView() {
        mIVZhihuHeader = findViewById(R.id.iv_zhihu_head);

        mWebView = findViewById(R.id.wv_zhihu);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());

        Toolbar toolbar = findViewById(R.id.tb_zhihu);
        final MarqueeText marqueeText = findViewById(R.id.toolbar_title);
        marqueeText.setText(mZhihuTitle);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        toolbarLayout.setTitle(mZhihuTitle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mZhihuTitle);
        }
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener(){
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    marqueeText.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    marqueeText.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                }
            }
        });
    }

    private void initToolbar() {

    }

    private void readIntent() {
        mZhihuId = this.getIntent().getStringExtra(ZHIHU_ID);
        mZhihuTitle = this.getIntent().getStringExtra(ZHIHU_TITLE);
    }
}
