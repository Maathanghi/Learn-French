package com.lgvalle.material_animations.viewpager

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.lgvalle.material_animations.R
import com.lgvalle.material_animations.model.translation.Quiz
import com.raywenderlich.favoritemovies.AppHelper
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() , View.OnClickListener {
  override fun onClick(v: View) {

  }

  private lateinit var mCongratzContainer: RelativeLayout
  private lateinit var quizContainer: RelativeLayout
  private lateinit var root: View
  private lateinit var imgQuiz: ImageView
  private lateinit var option1: Button
  private lateinit var option2: Button
  private lateinit var option3: Button
  private lateinit var option4: Button
  private lateinit var buttonSubmit: Button


  private fun findViews(view: View) {

    mCongratzContainer = view.findViewById(R.id.congratzContainer)
    quizContainer = view.findViewById(R.id.quizContainer)
    imgQuiz = view.findViewById(R.id.imgQuiz)
    option1 = view.findViewById(R.id.option1)
    option2 = view.findViewById(R.id.option2)
    option3 = view.findViewById(R.id.option3)
    option4 = view.findViewById(R.id.option4)
    buttonSubmit = view.findViewById(R.id.buttonSubmit)
  }



  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
  Bundle?): View? {

    // Creates the view controlled by the fragment
    val view = inflater.inflate(R.layout.fragment_quiz, container, false)

    root = view.findViewById(R.id.rootlayout)
    root.setOnClickListener(this)

    // Retrieve and display the movie data from the Bundle
    val args = arguments
    /*titleTextView.text = args.getString(AppHelper.KEY_TITLE)
    ratingTextView.text = String.format("%d/10", args.getInt(AppHelper.KEY_RATING))
    overviewTextView.text = args.getString(AppHelper.KEY_OVERVIEW)*/



    findViews(view)


    if(args.getString(AppHelper.KEY_TITLE).equals("congratulations"))
    {
      mCongratzContainer.visibility= View.VISIBLE
      quizContainer.visibility= View.GONE
    }else{
      quizContainer.visibility= View.VISIBLE
      mCongratzContainer.visibility= View.GONE
      // Download the image and display it using Picasso
      Picasso.with(activity)
              .load(resources.getIdentifier(args.getString(AppHelper.KEY_POSTER_URI), "drawable", activity.packageName))
              .into(imgQuiz)
      option1.text = args.getString(AppHelper.KEY_OPTION_1)
      option2.text = args.getString(AppHelper.KEY_OPTION_2)
      option3.text = args.getString(AppHelper.KEY_OPTION_3)
      option4.text = args.getString(AppHelper.KEY_OPTION_4)

    }


    return view
  }

  companion object {

    // Method for creating new instances of the fragment
    fun newInstance(quiz: Quiz): QuizFragment {

      // Store the movie data in a Bundle object
      val args = Bundle()
      args.putString(AppHelper.KEY_OPTION_1, quiz.option1)
      args.putString(AppHelper.KEY_OPTION_2, quiz.option2)
      args.putString(AppHelper.KEY_OPTION_3, quiz.option3)
      args.putString(AppHelper.KEY_OPTION_4, quiz.option4)
      args.putString(AppHelper.KEY_CORRECT_ANSWER, quiz.correct)
      args.putString(AppHelper.KEY_POSTER_URI, quiz.imageUri)
      args.putString(AppHelper.KEY_TITLE, quiz.title)
      //args.putString(AppHelper.KEY_OVERVIEW, lesson.overview)

      // Create a new LessonFragment and set the Bundle as the arguments
      // to be retrieved and displayed when the view is created
      val fragment = QuizFragment()
      fragment.arguments = args
      return fragment
    }
  }

}