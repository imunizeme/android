package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String pdf = "http://drive.google.com/viewerng/viewer?embedded=true&url=";
    private String site;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if(getIntent().getBooleanExtra("isPDF", false) == true){

            setTitle("Vacinas - Imunize.me");

            site = pdf;
            site += getIntent().getStringExtra("site");

        }else{
            setTitle("Notícias - Imunize.me");
            site = getIntent().getStringExtra("site");
        }


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);



        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);



        webView.loadUrl(site);
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}
