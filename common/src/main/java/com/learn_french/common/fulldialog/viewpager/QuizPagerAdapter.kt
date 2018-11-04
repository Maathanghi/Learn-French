package com.learn_french.common.fulldialog.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.learn_french.common.fulldialog.contracts.IListeners
import com.learn_french.common.fulldialog.model.app.Quiz

class QuizPagerAdapter(fragmentManager: FragmentManager, private val lessons: ArrayList<Quiz>, private val level: String,private val listner: IListeners) :
    FragmentStatePagerAdapter(fragmentManager) {

  // Return the Fragment associated with the object located at the specified position 
  override fun getItem(position: Int): Fragment {
    return QuizFragment.newInstance(lessons[position],level,listner)
  }

  // Return the number of objects in the array.  
  override fun getCount(): Int {
    return lessons.size
  }
}