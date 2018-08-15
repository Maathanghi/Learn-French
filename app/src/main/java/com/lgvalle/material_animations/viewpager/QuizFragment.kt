package com.lgvalle.material_animations.viewpager

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
import android.content.SharedPreferences
import android.graphics.Color
import android.speech.tts.TextToSpeech
import com.lgvalle.material_animations.contracts.IListeners
import java.util.*
import android.view.animation.AlphaAnimation




class QuizFragment : Fragment() , View.OnClickListener {
  private lateinit var textToSpeech: TextToSpeech
  private lateinit var iListner: IListeners

  override fun onClick(v: View) {
    val item_id = v.id
    when (item_id) {
      R.id.option1 -> changeButtonColor(v)
      R.id.option2 -> changeButtonColor(v)
      R.id.option3 -> changeButtonColor(v)
      R.id.option4 -> changeButtonColor(v)
      R.id.buttonNext -> iListner.nextQuestionListener()
      R.id.buttonSubmit -> iListner.takeHomeListener()
    }
  }

  fun changeButtonColor(view: View) {
    option1.setBackgroundResource(R.drawable.curved_button)
    option2.setBackgroundResource(R.drawable.curved_button)
    option3.setBackgroundResource(R.drawable.curved_button)
    option4.setBackgroundResource(R.drawable.curved_button)
    option1.setTextColor(Color.parseColor("#000000"))
    option2.setTextColor(Color.parseColor("#000000"))
    option3.setTextColor(Color.parseColor("#000000"))
    option4.setTextColor(Color.parseColor("#000000"))
    val b = view as Button
    val buttonText = b.text.toString()

    textToSpeech.setLanguage(Locale.ENGLISH)
    Toast.makeText(context, buttonText, Toast.LENGTH_SHORT).show()
    textToSpeech.speak(buttonText, TextToSpeech.QUEUE_FLUSH, null)

    val alphaUp = AlphaAnimation(0.45f, 0.45f)
    val alphaDown = AlphaAnimation(0.00f, 0.45f)
    alphaUp.fillAfter = true

    if(buttonText.equals(correctAnswer)){
      view.setBackgroundResource(R.drawable.dotted_correct_button)
      view.setTextColor(Color.parseColor("#ffffff"))
      buttonNext.isEnabled=true
      buttonNext.startAnimation(alphaDown)
    }else{
      view.setBackgroundResource(R.drawable.dotted_wrong_button)
      view.setTextColor(Color.parseColor("#ffffff"))
      buttonNext.isEnabled=false
      buttonNext.startAnimation(alphaUp)
    }

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
  private lateinit var buttonNext: Button
  private lateinit var correctAnswer: String



  var prefs: SharedPreferences? = null
  val PREFS_FILENAME = "MyPrefs"


  private fun findViews(view: View) {

    mCongratzContainer = view.findViewById(R.id.congratzContainer)
    quizContainer = view.findViewById(R.id.quizContainer)
    imgQuiz = view.findViewById(R.id.imgQuiz)
    option1 = view.findViewById(R.id.option1)
    option2 = view.findViewById(R.id.option2)
    option3 = view.findViewById(R.id.option3)
    option4 = view.findViewById(R.id.option4)
    buttonSubmit = view.findViewById(R.id.buttonSubmit)
    buttonNext = view.findViewById(R.id.buttonNext)

    option1.setOnClickListener(this)
    option2.setOnClickListener(this)
    option3.setOnClickListener(this)
    option4.setOnClickListener(this)
    buttonSubmit.setOnClickListener(this)
    buttonNext.setOnClickListener(this)
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

    prefs = context.getSharedPreferences(PREFS_FILENAME, 0)

    if(args!!.getString(AppHelper.KEY_TITLE).equals("congratulations"))
    {
      mCongratzContainer.visibility= View.VISIBLE
      quizContainer.visibility= View.GONE


      val editor = prefs!!.edit()
      editor.putInt(args.getString(AppHelper.KEY_LEVEL), 2)
      editor.apply()
    }else{

      val editor = prefs!!.edit()
      editor.putInt(args.getString(AppHelper.KEY_LEVEL), 1)
      editor.apply()


      quizContainer.visibility= View.VISIBLE
      mCongratzContainer.visibility= View.GONE
      // Download the image and display it using Picasso
      Picasso.with(activity)
              .load(resources.getIdentifier(args.getString(AppHelper.KEY_POSTER_URI), "drawable", activity!!.packageName))
              .into(imgQuiz)
      option1.text = args.getString(AppHelper.KEY_OPTION_1)
      option2.text = args.getString(AppHelper.KEY_OPTION_2)
      option3.text = args.getString(AppHelper.KEY_OPTION_3)
      option4.text = args.getString(AppHelper.KEY_OPTION_4)
      correctAnswer = args.getString(AppHelper.KEY_CORRECT_ANSWER)
    }
    iListner = args.getSerializable(AppHelper.KEY_LISTENER) as IListeners


    textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
      if (status != TextToSpeech.ERROR) {
        textToSpeech.setLanguage(Locale.FRANCE)
      }
    })

    return view
  }

  companion object {



    // Method for creating new instances of the fragment
    fun newInstance(quiz: Quiz,currentLevel: String,listner: IListeners): QuizFragment {

      // Store the movie data in a Bundle object
      val args = Bundle()
      args.putString(AppHelper.KEY_OPTION_1, quiz.option1)
      args.putString(AppHelper.KEY_OPTION_2, quiz.option2)
      args.putString(AppHelper.KEY_OPTION_3, quiz.option3)
      args.putString(AppHelper.KEY_OPTION_4, quiz.option4)
      args.putString(AppHelper.KEY_CORRECT_ANSWER, quiz.correct)
      args.putString(AppHelper.KEY_POSTER_URI, quiz.imageUri)
      args.putString(AppHelper.KEY_TITLE, quiz.title)
      args.putString(AppHelper.KEY_LEVEL, currentLevel)
      args.putSerializable(AppHelper.KEY_LISTENER, listner)
      //args.putString(AppHelper.KEY_OVERVIEW, lesson.overview)

      // Create a new LessonFragment and set the Bundle as the arguments
      // to be retrieved and displayed when the view is created
      val fragment = QuizFragment()
      fragment.arguments = args
      return fragment
    }
  }

}