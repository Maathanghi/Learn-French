
package com.learn_french.common.fulldialog.viewpager

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.learn_french.common.R
import com.learn_french.common.fulldialog.contracts.IListeners
import com.learn_french.common.fulldialog.model.app.Lesson

import com.squareup.picasso.Picasso
import java.util.*
import android.support.design.widget.Snackbar
import android.widget.TextView
import com.learn_french.common.fulldialog.roomdatabse.database.AppDatabase
import com.learn_french.common.fulldialog.roomdatabse.utils.DatabaseInitializer


class LessonFragment : Fragment() , View.OnClickListener{
  override fun onClick(v: View) {
    val item_id = v.id
    when (item_id) {
      //R.id.rootlayout -> flipCard(v)
      R.id.card_back -> flipCard(v)
      R.id.card_front -> flipCard(v)
      R.id.imgFrench -> textToSpeech(v)
      R.id.imgEnglish -> textToSpeech(v)
      R.id.ic_fav -> startCheckAnimation()
      R.id.buttonQuiz -> iListner.quizClickListener()
    }
  }

  private lateinit var mSetRightOut: AnimatorSet
  private lateinit var mSetLeftIn: AnimatorSet
  private var mIsBackVisible = false
  private lateinit var mCardFrontLayout: View
  private lateinit var mCardBackLayout: View
  private lateinit var mIncludeBack: View
  private lateinit var mIncludeFront: View
  private lateinit var mFrenchText: TextView
  private lateinit var mEnglishText: TextView
  private lateinit var textToSpeech: TextToSpeech
  private lateinit var mImage: ImageView
  private lateinit var mImageFrechTtoSpeech: ImageView
  private lateinit var mImageEnglishtoSpeech: ImageView
  private lateinit var mCongratzContainer: RelativeLayout
  private lateinit var buttonQuiz: Button
  private lateinit var txtLevel: TextView
  private lateinit var root: View
  private lateinit var iListner: IListeners
  private lateinit var lesson: Lesson
  private lateinit var favIcon : LottieAnimationView

  private fun changeCameraDistance() {
    val distance = 8000
    val scale = resources.displayMetrics.density * distance
    mCardFrontLayout.setCameraDistance(scale)
    mCardBackLayout.setCameraDistance(scale)
  }

  private fun startCheckAnimation() {
    val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(500)
    animator.addUpdateListener { valueAnimator -> favIcon.setProgress(valueAnimator.animatedValue as Float) }

    if (favIcon.getProgress() === 0f) {
      DatabaseInitializer.bookmarkALesson(AppDatabase.getAppDatabase(context), lesson);
      enableBookMark()
      showSnackBar("Added to Bookmark")
    } else {
      DatabaseInitializer.removeBookmark(AppDatabase.getAppDatabase(context),lesson.title)
      removeBookMark()
      showSnackBar("Removed from Bookmark")
    }
  }

  private fun isBookmarked() {
    if(DatabaseInitializer.isBookmarked(AppDatabase.getAppDatabase(context),lesson.title)){
      enableBookMark()
    }else{
      removeBookMark()
    }
  }

  private fun enableBookMark() {
    val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(500)
    animator.addUpdateListener { valueAnimator -> favIcon.setProgress(valueAnimator.animatedValue as Float) }
    animator.start()
  }

  private fun removeBookMark() {
    favIcon.progress= 0F
  }

  private fun showSnackBar(msg: String){
    val snackbar = Snackbar.make(root, msg, Snackbar.LENGTH_SHORT)
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(resources.getColor(R.color.app_color_3))
    val textView = snackBarView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
    textView.setTextColor(resources.getColor(R.color.white))
    snackbar.show()
  }


  private fun loadAnimations() {
    mSetRightOut = AnimatorInflater.loadAnimator(context, R.animator.out_animation) as AnimatorSet
    mSetLeftIn = AnimatorInflater.loadAnimator(context, R.animator.in_animation) as AnimatorSet
  }

  private fun findViews(view: View) {
    mCardBackLayout = view.findViewById(R.id.card_back)
    mCardFrontLayout = view.findViewById(R.id.card_front)
    mIncludeBack = view.findViewById(R.id.include_back)
    mIncludeFront = view.findViewById(R.id.include_front)
    mFrenchText = mIncludeBack.findViewById(R.id.frenchText)
    mEnglishText = mIncludeBack.findViewById(R.id.englishText)
    mImage = mIncludeFront.findViewById(R.id.cardImage)
    mImageFrechTtoSpeech = mIncludeBack.findViewById(R.id.imgFrench)
    mImageEnglishtoSpeech = mIncludeBack.findViewById(R.id.imgEnglish)
    favIcon = view.findViewById(R.id.ic_fav)
    favIcon.setProgress(0f)

    mCongratzContainer = view.findViewById(R.id.congratzContainer)
    buttonQuiz = view.findViewById(R.id.buttonQuiz)
    txtLevel = view.findViewById(R.id.txtLevel)

    buttonQuiz.setOnClickListener(this)
    mCardBackLayout.setOnClickListener(this)
    mCardFrontLayout.setOnClickListener(this)
    favIcon.setOnClickListener(this)
  }

  fun flipCard(view: View) {
    if (!mIsBackVisible) {
      mSetRightOut.setTarget(mCardFrontLayout)
      mSetLeftIn.setTarget(mCardBackLayout)
      mSetRightOut.start()
      mSetLeftIn.start()
      mIsBackVisible = true
      textToSpeech(mImageFrechTtoSpeech)
    } else {
      mSetRightOut.setTarget(mCardBackLayout)
      mSetLeftIn.setTarget(mCardFrontLayout)
      mSetRightOut.start()
      mSetLeftIn.start()
      mIsBackVisible = false
    }
  }

  fun textToSpeech(view: View) {

    if(view.id == R.id.imgEnglish){
      textToSpeech.setLanguage(Locale.ENGLISH)
      val toSpeak = mEnglishText.getText().toString()
      //Toast.makeText(context, toSpeak, Toast.LENGTH_SHORT).show()
      textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
    }else{
      textToSpeech.setLanguage(Locale.FRANCE)
      val toSpeak = mFrenchText.getText().toString()
      //Toast.makeText(context, toSpeak, Toast.LENGTH_SHORT).show()
      textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
    }
  }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
  Bundle?): View? {

    // Creates the view controlled by the fragment
    val view = inflater.inflate(R.layout.fragment_lesson, container, false)
    /*val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
    val ratingTextView = view.findViewById<TextView>(R.id.ratingTextView)
    val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)
    val overviewTextView = view.findViewById<TextView>(R.id.overviewTextView)*/

    mCardBackLayout = view.findViewById(R.id.card_back)
    mCardFrontLayout = view.findViewById(R.id.card_front)
    root = view.findViewById(R.id.rootlayout)
    root.setOnClickListener(this)

    // Retrieve and display the movie data from the Bundle
    val args = arguments
    /*titleTextView.text = args.getString(AppHelper.KEY_TITLE)
    ratingTextView.text = String.format("%d/10", args.getInt(AppHelper.KEY_RATING))
    overviewTextView.text = args.getString(AppHelper.KEY_OVERVIEW)*/



    findViews(view)
    loadAnimations()
    changeCameraDistance()



    if(args?.getString(AppHelper.KEY_TITLE).equals("congratulations"))
    {
      mCardBackLayout.visibility= View.GONE
      mCardFrontLayout.visibility= View.GONE
      favIcon.visibility= View.GONE
      mCongratzContainer.visibility= View.VISIBLE
      if (args != null) {
        txtLevel.text = args.getString(AppHelper.KEY_LEVEL)
      }
    }else{
      // Download the image and display it using Picasso
      if (args != null) {
        Picasso.with(activity)
                .load(resources.getIdentifier(args.getString(AppHelper.KEY_POSTER_URI), "drawable", activity?.packageName))
                .into(mImage)
        mEnglishText.text = args.getString(AppHelper.KEY_ENGLISH_TRANSLATION)
        mFrenchText.text = args.getString(AppHelper.KEY_FRENCH_TRANSLATION)
      }
    }
      if (args != null) {
        iListner = args.getSerializable(AppHelper.KEY_LISTENER) as IListeners
        lesson = args.getSerializable(AppHelper.KEY_LESSON) as Lesson
      }
      mImageFrechTtoSpeech.setOnClickListener(this)
      mImageEnglishtoSpeech.setOnClickListener(this)

      textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
        if (status != TextToSpeech.ERROR) {
          textToSpeech.setLanguage(Locale.ENGLISH)
        }
      })

      isBookmarked()

    return view
  }


  override fun onPause() {
    if (textToSpeech != null) {
      textToSpeech.stop()
      textToSpeech.shutdown()
    }
    super.onPause()
  }
  companion object {

    // Method for creating new instances of the fragment
    fun newInstance(lesson: Lesson,listner: IListeners,level: String): LessonFragment {

      // Store the movie data in a Bundle object
      val args = Bundle()
      args.putString(AppHelper.KEY_ENGLISH_TRANSLATION, lesson.englishTranslation)
      args.putString(AppHelper.KEY_FRENCH_TRANSLATION, lesson.frenchTranslation)
      args.putString(AppHelper.KEY_POSTER_URI, lesson.imageUri)
      args.putString(AppHelper.KEY_TITLE, lesson.title)
      args.putString(AppHelper.KEY_LEVEL, level)
      args.putSerializable(AppHelper.KEY_LISTENER, listner)
      args.putSerializable(AppHelper.KEY_LESSON, lesson)
      //args.putString(AppHelper.KEY_OVERVIEW, lesson.overview)

      // Create a new LessonFragment and set the Bundle as the arguments
      // to be retrieved and displayed when the view is created
      val fragment = LessonFragment()
      fragment.arguments = args
      return fragment
    }
  }

}
