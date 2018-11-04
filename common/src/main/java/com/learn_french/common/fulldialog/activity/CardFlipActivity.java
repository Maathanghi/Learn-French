package com.learn_french.common.fulldialog.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.learn_french.common.R;
import com.learn_french.common.fulldialog.contracts.IListeners;
import com.learn_french.common.fulldialog.model.app.App;
import com.learn_french.common.fulldialog.utils.Utils;
import com.learn_french.common.fulldialog.viewpager.AppHelper;
import com.learn_french.common.fulldialog.viewpager.LessonPagerAdapter;
import com.learn_french.common.fulldialog.viewpager.QuizPagerAdapter;

import java.util.ArrayList;

public class CardFlipActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener,IListeners {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    public static final String EXTRA_SELECTED_LEVEL = "EXTRA_SELECTED_LEVEL";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    View rootLayout;

    private int revealX;
    private int revealY;
    private String selectedLevel;
    private boolean isLesson;

    private ViewPager viewPager;
    private ProgressBar progressBar;
    private LessonPagerAdapter pagerLessonAdapter;
    private QuizPagerAdapter pagerQuizAdapter;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardflip);

        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (isLesson) {
            toolbar.setTitle(selectedLevel+" - Lesson");
        } else {
            toolbar.setTitle(selectedLevel+" - Quiz");
        }
        toolbar.setTitle(selectedLevel);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y) &&
                intent.hasExtra(EXTRA_CATEGORY)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            selectedLevel = intent.getStringExtra(EXTRA_SELECTED_LEVEL);
            isLesson = intent.getBooleanExtra(EXTRA_CATEGORY, false);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Utils.revealActivity(revealX, revealY, rootLayout, CardFlipActivity.this);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }


        initiateLevelViewPager(isLesson);
    }


    private App getLesson(String title, ArrayList<App> appData){
        for(App app : appData) {
            if(app.getTitle().equalsIgnoreCase(title)) {
                return app;
            }
        }
        return null;
    }


    private void initiateLevelViewPager(boolean isLesson){
        // Get the list of movies from the JSON file
        ArrayList<App> appData = AppHelper.getAppDataFromJson("app.json", this);

        app = getLesson(selectedLevel,appData);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // Initialize the LessonPagerAdapter
        if(isLesson) {
            pagerLessonAdapter = new LessonPagerAdapter(getSupportFragmentManager(), app.getLesson(),this,app.getTitle());
            // Set the Adapter and the TabLayout for the ViewPager
            viewPager.setAdapter(pagerLessonAdapter);
            progressBar.setMax(app.getLesson().size()-1);
        }else{
            pagerQuizAdapter = new QuizPagerAdapter(getSupportFragmentManager(), app.getQuiz(),app.getTitle(),this);
            // Set the Adapter and the TabLayout for the ViewPager
            viewPager.setAdapter(pagerQuizAdapter);
            progressBar.setMax(app.getQuiz().size()-1);
        }


        viewPager.addOnPageChangeListener(this);
        //tabLayout.setupWithViewPager(viewPager);

        progressBar.setProgress(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void quizClickListener() {

    }

    @Override
    public void takeHomeListener() {

    }

    @Override
    public void nextQuestionListener() {

    }

    @Override
    public ViewPager getViewPagerListener() {
        return null;
    }
}
