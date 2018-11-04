package com.learn_french.common.fulldialog.activity;

import android.content.Intent;
import android.os.Build;
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

public class CardOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    public static String EXTRA_SELECTED_LEVEL = "EXTRA_SELECTED_LEVEL";
    private String selectedLevel;

    View outerView;
    LinearLayout mLesson;
    LinearLayout mQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardoverview);

        final Intent intent = getIntent();
        if (savedInstanceState == null && intent.hasExtra(EXTRA_SELECTED_LEVEL) ) {
            selectedLevel = intent.getStringExtra(EXTRA_SELECTED_LEVEL);
        }

        mLesson = (LinearLayout) findViewById(R.id.lesson);
        mQuiz = (LinearLayout) findViewById(R.id.exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(selectedLevel);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        outerView = (RelativeLayout) findViewById(R.id.reveal_status);
        Utils.startFadeInAnimation(outerView, getApplicationContext());

        mLesson.setOnClickListener(this);
        mQuiz.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Utils.startFadeOutAnimation(outerView, getApplicationContext());
        return true;
    }

    public void presentActivity(View view, boolean isLesson) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, CardFlipActivity.class);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        intent.putExtra(CardFlipActivity.EXTRA_SELECTED_LEVEL, selectedLevel);
        intent.putExtra(CardFlipActivity.EXTRA_CATEGORY, isLesson);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.lesson){
            presentActivity(view, true);
        }else if(view.getId()==R.id.exam){
            presentActivity(view, false);
        }
    }
}