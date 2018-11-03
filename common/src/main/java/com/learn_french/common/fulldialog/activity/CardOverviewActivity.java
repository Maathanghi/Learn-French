package com.learn_french.common.fulldialog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.learn_french.common.R;
import com.learn_french.common.fulldialog.utils.Utils;

public class CardOverviewActivity extends AppCompatActivity {
    View outerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardoverview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        outerView = (RelativeLayout) findViewById(R.id.reveal_status);
        Utils.startFadeInAnimation(outerView, getApplicationContext());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Utils.startFadeOutAnimation(outerView, getApplicationContext());
        return true;
    }

}