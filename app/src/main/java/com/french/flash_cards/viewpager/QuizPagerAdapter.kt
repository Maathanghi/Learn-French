package com.french.flash_cards.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.french.flash_cards.contracts.IListeners
import com.french.flash_cards.model.translation.Lesson
import com.french.flash_cards.model.translation.Quiz
import com.raywenderlich.favoritemovies.LessonFragment

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