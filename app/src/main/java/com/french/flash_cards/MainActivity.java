package com.french.flash_cards;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.airbnb.lottie.LottieAnimationView;
import com.french.flash_cards.model.translation.App;
import com.raywenderlich.favoritemovies.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Sample> samples;
    SharedPreferences sharedpreferences;
    ObjectAnimator objectanimator;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LottieAnimationView cycleAnim = (LottieAnimationView) findViewById(R.id.cycle_anim);
        objectanimator = ObjectAnimator.ofFloat(cycleAnim,"x",300);
        objectanimator.setDuration(4000);
        objectanimator.start();

        setupToolbar();
        setupSamples();
        setupLayout();
    }

    @Override
    protected void onResume() {
        //setupWindowAnimations();
        //setupSamples();
        //setupToolbar();
        //setupLayout();
        objectanimator.start();
        super.onResume();
    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private void setupSamples() {
        ArrayList<App> appData = AppHelper.getAppDataFromJson("app.json", this);
        samples = new ArrayList<>();
        int i =0;
        for(App level : appData) {
            if(!level.getTitle().contains("Header")){
                i++;
            }

            // Do something with the value
            int color = Color.parseColor(level.getColor());
            samples.add(new Sample(color, level.getTitle(),i,getLevelStatus(level.getTitle())));
        }
    }

    private int getLevelStatus(String title){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return sharedpreferences.getInt(title,0);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sample_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(this, samples);
        recyclerView.setAdapter(homeRecyclerAdapter);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        recyclerView.setLayoutAnimation(animation);
    }
}
