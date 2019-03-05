package co.windly.ktxprefs.sample.kotlinsimple

import android.app.Activity
import android.os.Bundle
import android.util.Log
import co.windly.ktxprefs.sample.kotlinsimple.cache.requireUserCache

class MainActivity : Activity() {

  //region Companion

  companion object {
    const val TAG = "MainActivity"
  }

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize cache.
    initializeUserCache()
  }

  //endregion

  //region Initialize Cache

  private fun initializeUserCache() {

    // Get access to shared preferences wrapper.
    val cache = requireUserCache()

    // Put a single value (apply() is automatically called).
    cache
      .putId(1L)

    // Put several values in one transaction.
    cache
      .edit()
      .putFirstName("John")
      .putLastName("Snow")
      .putPassword("WinterIsComing")
      .putActive(true)
      .commit()

    // Check if a value is set.
    if (cache.containsFirstName()) {
      Log.d(TAG, "First name is set.")
    }

    // Access preferences one by one.
    with(cache) {
      Log.d(TAG, "id -> ${getId()}.")
      Log.d(TAG, "first name -> ${getFirstName()}.")
      Log.d(TAG, "last name -> ${getLastName()}.")
      Log.d(TAG, "password -> ${getPassword()}.")
      Log.d(TAG, "active -> ${isActive()}.")
    }

    // Access all preferences.
    Log.d(TAG, "cache -> ${cache.all}.")

    // Remove a value.
    cache.removeFirstName()

    // Clear all preferences.
    cache.clear()
  }

  //endregion
}
