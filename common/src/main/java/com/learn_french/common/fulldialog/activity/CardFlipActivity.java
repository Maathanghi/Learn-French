package com.learn_french.common.fulldialog.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.learn_french.common.R;
import com.learn_french.common.fulldialog.contracts.IListeners;
import com.learn_french.common.fulldialog.model.app.App;
import com.learn_french.common.fulldialog.model.app.AppDataUtil;
import com.learn_french.common.fulldialog.model.app.Lesson;
import com.learn_french.common.fulldialog.utils.Utils;
import com.learn_french.common.fulldialog.viewpager.LessonPagerAdapter;
import com.learn_french.common.fulldialog.viewpager.QuizPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardFlipActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener,IListeners {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    public static final String EXTRA_SELECTED_LEVEL = "EXTRA_SELECTED_LEVEL";
    public static final String EXTRA_Is_LESSON = "EXTRA_Is_LESSON";
    public static final String EXTRA_IS_CATEGORY = "EXTRA_Is_CATEGORY";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    View rootLayout;

    private int revealX;
    private int revealY;
    private String selectedLevel;
    private boolean isLesson;

    private boolean isCategory;
    private String selectedCategory;

    private ViewPager viewPager;
    private ProgressBar progressBar;
    private LessonPagerAdapter pagerLessonAdapter;
    private QuizPagerAdapter pagerQuizAdapter;
    private App app;
    ArrayList<Lesson> lessonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardflip);

        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);

        if(intent.hasExtra(EXTRA_IS_CATEGORY) && intent.hasExtra(EXTRA_CATEGORY_NAME)){
            isCategory = intent.getBooleanExtra(EXTRA_IS_CATEGORY, false);
            selectedCategory = intent.getStringExtra(EXTRA_CATEGORY_NAME);
        }
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y) &&
                intent.hasExtra(EXTRA_Is_LESSON)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            selectedLevel = intent.getStringExtra(EXTRA_SELECTED_LEVEL);
            isLesson = intent.getBooleanExtra(EXTRA_Is_LESSON, false);


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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (isLesson) {
            if(isCategory) toolbar.setTitle("Category "+selectedCategory);
            else           toolbar.setTitle(selectedLevel+" - Lesson");
        } else {
            toolbar.setTitle(selectedLevel+" - Quiz");
        }
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        initiateLevelViewPager(isLesson);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void initiateLevelViewPager(boolean isLesson){
        // Get the list of movies from the JSON file
        ArrayList<App> appData = AppDataUtil.getInstance().getAppList(getApplicationContext());

        app = AppDataUtil.getInstance().getLesson(selectedLevel,appData);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // Initialize the LessonPagerAdapter
        if(isLesson) {
            String title;
             if(isCategory){
                 lessonList = AppDataUtil.getInstance().getMyCategoryListByName(getApplicationContext(),selectedCategory);
                 title = selectedCategory;
             }else{
                 lessonList = app.getLesson();
                 title = app.getTitle();
             }
            pagerLessonAdapter = new LessonPagerAdapter(getSupportFragmentManager(), lessonList,this,title);
            // Set the Adapter and the TabLayout for the ViewPager
            viewPager.setAdapter(pagerLessonAdapter);
            progressBar.setMax(lessonList.size()-1);
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
        progressBar.setProgress(position);
        if(lessonList.size() - 1 == position)
        {
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void quizClickListener() {
        initiateLevelViewPager(false);
    }

    @Override
    public void takeHomeListener() {
        onBackPressed();
    }

    @Override
    public void nextQuestionListener() {
        viewPager.setCurrentItem(progressBar.getProgress() + 1);
    }

    @Override
    public ViewPager getViewPagerListener() {
        return viewPager;
    }
}
