/*
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.favoritemovies

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lgvalle.material_animations.R
import com.lgvalle.material_animations.model.Movie

import com.squareup.picasso.Picasso

class MovieFragment : Fragment() , View.OnClickListener{
  override fun onClick(v: View) {
    val item_id = v.id
    when (item_id) {
      R.id.rootlayout -> flipCard(v)
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
  private lateinit var mImage: ImageView
  private lateinit var root: View
  private fun changeCameraDistance() {
    val distance = 8000
    val scale = resources.displayMetrics.density * distance
    mCardFrontLayout.setCameraDistance(scale)
    mCardBackLayout.setCameraDistance(scale)
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
  }

  fun flipCard(view: View) {
    if (!mIsBackVisible) {
      mSetRightOut.setTarget(mCardFrontLayout)
      mSetLeftIn.setTarget(mCardBackLayout)
      mSetRightOut.start()
      mSetLeftIn.start()
      mIsBackVisible = true
    } else {
      mSetRightOut.setTarget(mCardBackLayout)
      mSetLeftIn.setTarget(mCardFrontLayout)
      mSetRightOut.start()
      mSetLeftIn.start()
      mIsBackVisible = false
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
  Bundle?): View? {

    // Creates the view controlled by the fragment
    val view = inflater.inflate(R.layout.fragment_movie, container, false)
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

    // Download the image and display it using Picasso
    Picasso.with(activity)
         .load(resources.getIdentifier(args.getString(AppHelper.KEY_POSTER_URI), "drawable", activity.packageName))
         .into(mImage)
    mEnglishText.text = args.getString(AppHelper.KEY_TITLE)
    mFrenchText.text = args.getString(AppHelper.KEY_TITLE)


    return view
  }

  companion object {

    // Method for creating new instances of the fragment
    fun newInstance(movie: Movie): MovieFragment {

      // Store the movie data in a Bundle object
      val args = Bundle()
      args.putString(AppHelper.KEY_TITLE, movie.title)
      args.putInt(AppHelper.KEY_RATING, movie.rating)
      args.putString(AppHelper.KEY_POSTER_URI, movie.posterUri)
      args.putString(AppHelper.KEY_OVERVIEW, movie.overview)

      // Create a new MovieFragment and set the Bundle as the arguments
      // to be retrieved and displayed when the view is created
      val fragment = MovieFragment()
      fragment.arguments = args
      return fragment
    }
  }

}
