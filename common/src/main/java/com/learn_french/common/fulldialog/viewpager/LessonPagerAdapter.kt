

package com.learn_french.common.fulldialog.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.learn_french.common.fulldialog.contracts.IListeners
import com.learn_french.common.fulldialog.model.app.Lesson

class LessonPagerAdapter(fragmentManager: FragmentManager, private val lessons: ArrayList<Lesson>,private val listner: IListeners,private val level: String) :
    FragmentStatePagerAdapter(fragmentManager) {

  // Return the Fragment associated with the object located at the specified position 
  override fun getItem(position: Int): Fragment {
    return LessonFragment.newInstance(lessons[position],listner,level)
  }

  // Return the number of objects in the array.  
  override fun getCount(): Int {
    return lessons.size
  }
}
