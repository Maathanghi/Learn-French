package com.lgvalle.material_animations.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lgvalle.material_animations.model.translation.Lesson
import com.lgvalle.material_animations.model.translation.Quiz
import com.raywenderlich.favoritemovies.LessonFragment

class QuizPagerAdapter(fragmentManager: FragmentManager, private val lessons: ArrayList<Quiz>) :
    FragmentStatePagerAdapter(fragmentManager) {

  // Return the Fragment associated with the object located at the specified position 
  override fun getItem(position: Int): Fragment {
    return QuizFragment.newInstance(lessons[position])
  }

  // Return the number of objects in the array.  
  override fun getCount(): Int {
    return lessons.size
  }
}