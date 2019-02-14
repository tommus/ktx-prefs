package co.windly.ktxprefs.sample.kotlinsimple

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Get access to shared preferences wrapper.
//    val cache = UserCachePrefs.get(this)

    // TODO:
  }

  //endregion
}
