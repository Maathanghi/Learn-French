package com.french.flash_cards.contracts;

import android.support.v4.view.ViewPager;

import java.io.Serializable;

/**
 * Created by anand on 15/08/2018.
 */

public interface IListeners extends Serializable{
    void quizClickListener();
    void takeHomeListener();
    void nextQuestionListener();
    ViewPager getViewPagerListener();
}
