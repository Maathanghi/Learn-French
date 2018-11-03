package com.learn_french.common.fulldialog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.learn_french.common.R;
import com.learn_french.common.fulldialog.utils.Utils;

public class CardOverviewActivity extends AppCompatActivity {
    View outerView;
    LinearLayout mLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardoverview);

        mLesson = (LinearLayout) findViewById(R.id.lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        outerView = (RelativeLayout) findViewById(R.id.reveal_status);
        Utils.startFadeInAnimation(outerView, getApplicationContext());

        mLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Utils.startFadeOutAnimation(outerView, getApplicationContext());
        return true;
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, CardFlipActivity.class);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}