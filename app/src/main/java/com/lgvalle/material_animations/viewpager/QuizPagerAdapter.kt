package com.lgvalle.material_animations.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lgvalle.material_animations.contracts.IListeners
import com.lgvalle.material_animations.model.translation.Lesson
import com.lgvalle.material_animations.model.translation.Quiz
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