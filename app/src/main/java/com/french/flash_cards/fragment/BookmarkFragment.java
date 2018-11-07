package com.french.flash_cards.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.french.flash_cards.R;
import com.learn_french.common.fulldialog.contracts.IListeners;
import com.learn_french.common.fulldialog.model.app.Lesson;
import com.learn_french.common.fulldialog.roomdatabse.database.AppDatabase;
import com.learn_french.common.fulldialog.roomdatabse.utils.DatabaseInitializer;
import com.learn_french.common.fulldialog.viewpager.LessonPagerAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment implements IListeners , ViewPager.OnPageChangeListener{
    private LessonPagerAdapter pagerLessonAdapter;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    ArrayList<Lesson> lessonList;
    private LinearLayout bookmarkEmpty;
    private RelativeLayout bookmarkLesson;


    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        bookmarkEmpty = (LinearLayout)rootView.findViewById(R.id.bookmarkEmpty);
        bookmarkLesson = (RelativeLayout)rootView.findViewById(R.id.bookmarkLesson);

        if(DatabaseInitializer.getTotal(AppDatabase.getAppDatabase(getContext())) > 0){
            bookmarkEmpty.setVisibility(View.GONE);
            bookmarkLesson.setVisibility(View.VISIBLE);
        }else{
            bookmarkEmpty.setVisibility(View.VISIBLE);
            bookmarkLesson.setVisibility(View.GONE);
        }
        lessonList = DatabaseInitializer.getAllBookmark(AppDatabase.getAppDatabase(getContext()));
        pagerLessonAdapter = new LessonPagerAdapter(getFragmentManager(), lessonList,this,"");
        // Set the Adapter and the TabLayout for the ViewPager
        viewPager.setAdapter(pagerLessonAdapter);
        progressBar.setMax(lessonList.size()-1);
        return rootView;
    }

    @Override
    public void quizClickListener() {
    }

    @Override
    public void takeHomeListener() {
    }

    @Override
    public void nextQuestionListener() {
        viewPager.setCurrentItem(progressBar.getProgress() + 1);
    }

    @Override
    public ViewPager getViewPagerListener() {
        return viewPager;
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
}
