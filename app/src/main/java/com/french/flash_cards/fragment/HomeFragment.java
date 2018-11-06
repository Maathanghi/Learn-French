package com.french.flash_cards.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.airbnb.lottie.LottieAnimationView;
import com.french.flash_cards.R;
import com.french.flash_cards.Sample;
import com.french.flash_cards.HomeRecyclerAdapter;
import com.learn_french.common.fulldialog.model.app.App;
import com.learn_french.common.fulldialog.model.app.AppDataUtil;
import com.raywenderlich.favoritemovies.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Sample> samples;
    SharedPreferences sharedpreferences;
    ObjectAnimator objectanimator;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        LottieAnimationView cycleAnim = (LottieAnimationView) rootView.findViewById(R.id.cycle_anim);
        objectanimator = ObjectAnimator.ofFloat(cycleAnim,"x",300);
        objectanimator.setDuration(4000);
        objectanimator.start();
        return rootView;
    }



    @Override
    public void onResume() {
        //setupWindowAnimations();
        setupSamples();
        setupToolbar();
        setupLayout();
        objectanimator.start();
        super.onResume();
    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        getActivity().getWindow().setReenterTransition(slideTransition);
        getActivity().getWindow().setExitTransition(slideTransition);
    }

    private void setupSamples() {
        ArrayList<App> appData = AppDataUtil.getInstance().getAppList(getContext());
        samples = new ArrayList<>();
        int i =0;
        for(App level : appData) {
            if(!level.getTitle().contains("Header")){
                i++;
            }

            // Do something with the value
            int color = Color.parseColor(level.getColor());
            samples.add(new Sample(color, level.getTitle(),i,getLevelStatus(level.getTitle()),level.getLesson().size() -1 +" words"));
        }
    }

    private int getLevelStatus(String title){
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return sharedpreferences.getInt(title,0);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.sample_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), samples);
        recyclerView.setAdapter(homeRecyclerAdapter);
        //int resId = R.anim.layout_animation_fall_down;
        //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        //recyclerView.setLayoutAnimation(null);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line));
        recyclerView.addItemDecoration(decoration);
    }
}
