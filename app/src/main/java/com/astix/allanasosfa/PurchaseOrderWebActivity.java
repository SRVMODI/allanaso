package com.astix.allanasosfa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.utils.AppUtils;

import java.util.regex.Pattern;

public class PurchaseOrderWebActivity extends BaseActivity {

    public static final String REPORT_TYPE_KEY = "reportType";
    // private static final String url = "http://103.20.212.194/ltace_dev/frmLoginPDA.aspx?IMEI=";
    String completeURL = "http://www.google.com";
    int nodeId;
    int nodeType;
    private WebView mWebView;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private int reportType;
    private AppDataSource mAppDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_web);

        mWebView = findViewById(R.id.web_view_imageStore);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppDataSource = new AppDataSource(this);
        reportType = getIntent().getExtras().getInt(REPORT_TYPE_KEY);
        if (reportType == 1) {
            String storeID = getIntent().getExtras().getString(AppUtils.StoreId);
            completeURL = CommonInfo.STORE_DETAILS_WEB_URL + storeID;
            getSupportActionBar().setTitle("Store Details");
            if (isOnline()) {
                initWebView();
            } else {
                showNoConnAlert();
            }
        } else if (reportType == 2) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    String str = mAppDataSource.fnGetPersonNodeIDAndPersonNodeType();
                    if (str != null) {
                        nodeId = Integer.valueOf(str.split(Pattern.quote("^"))[0]);
                        nodeType = Integer.valueOf(str.split(Pattern.quote("^"))[1]);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    completeURL = CommonInfo.DSR_TRACKER_WEB_URL + nodeId + "&ntype=" + nodeType;
                    getSupportActionBar().setTitle("DSR Tracker");
                    if (isOnline()) {
                        initWebView();
                    } else {
                        showNoConnAlert();
                    }
                }
            }.execute();
        } else if (reportType == 3) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    String str = mAppDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
                    if (str != null) {
                        nodeId = Integer.valueOf(str.split(Pattern.quote("^"))[0]);
                        nodeType = Integer.valueOf(str.split(Pattern.quote("^"))[1]);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
//                    completeURL = CommonInfo.DAILY_TRACKER_WEB_URL +nodeId + "&ntype=" + nodeType;
                    completeURL = CommonInfo.DAILY_TRACKER_WEB_URL +nodeId + "&ntype=" + nodeType;
                    getSupportActionBar().setTitle("Salesman Wise Summary");
                    if (isOnline()) {
                        initWebView();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    } else {
                        showNoConnAlert();
                    }
                }
            }.execute();
        }
    }


    private void initWebView() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
           }
       },100);
         mProgressDialog = ProgressDialog.show(PurchaseOrderWebActivity.this, "", "Loading Page...", false);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeClient());
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setUseWideViewPort(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        // String completeURL = CommonInfo.PURCHASE_ORDER_WEB_URL + CommonInfo.imei;
        mWebView.loadUrl(completeURL);
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

}
