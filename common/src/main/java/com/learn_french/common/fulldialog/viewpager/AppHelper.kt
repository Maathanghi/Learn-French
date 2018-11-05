

package com.learn_french.common.fulldialog.viewpager

import android.content.Context
import java.io.IOException
import com.google.gson.GsonBuilder
import com.learn_french.common.fulldialog.model.app.App
import com.learn_french.common.fulldialog.model.app.AppData
import java.util.*


object AppHelper {

  val KEY_ENGLISH_TRANSLATION = "title"
  val KEY_FRENCH_TRANSLATION = "rating"
  val KEY_POSTER_URI = "posterUri"
  val KEY_TITLE = "overview"
  val KEY_LEVEL = "currentLevel"
  val KEY_OPTION_1 = "KEY_OPTION_1"
  val KEY_OPTION_2 = "KEY_OPTION_2"
  val KEY_OPTION_3 = "KEY_OPTION_3"
  val KEY_OPTION_4 = "KEY_OPTION_4"
  val KEY_CORRECT_ANSWER = "KEY_CORRECT_ANSWER"
  val KEY_LISTENER = "KEY_LISTENER"
  var app = ArrayList<App>()


  @JvmStatic
  fun getAppDataFromJson(fileName: String, context: Context): ArrayList<App> {
    if(app.size == 0) {
      var app = ArrayList<App>()
      var appData: AppData

      // Load the JSONArray from the file
      val jsonString = loadJsonFromFile(fileName, context)
      appData = parseAppDataJSON(jsonString);
      app = appData.app

      return app
    }else{
      return app
    }
  }



  fun parseAppDataJSON(response: String): AppData {
    val gson = GsonBuilder().create()
    return gson.fromJson<AppData>(response, AppData::class.java!!)
  }


  private fun loadJsonFromFile(filename: String, context: Context): String {
    var json = ""

    try {
      val input = context.assets.open(filename)
      val size = input.available()
      val buffer = ByteArray(size)
      input.read(buffer)
      input.close()
      json = buffer.toString(Charsets.UTF_8)
    } catch (e: IOException) {
      e.printStackTrace()
    }

    return json
  }
}