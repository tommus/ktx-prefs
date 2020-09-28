package co.windly.ktxprefs.sample.kotlin.presentation.simple

import android.os.Bundle
import android.util.Log
import co.windly.ktxprefs.sample.kotlin.R
import co.windly.ktxprefs.sample.kotlin.persistence.shared.cache.UserCache
import dagger.android.AndroidInjection
import dagger.android.DaggerActivity
import javax.inject.Inject

class SimpleActivity : DaggerActivity() {

  //region Companion

  companion object {
    const val TAG = "MainActivity"
  }

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {

    // Inject dependencies.
    AndroidInjection.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize cache.
    initializeUserCache()
  }

  //endregion

  //region Initialize Cache

  @Inject
  lateinit var cache: UserCache

  private fun initializeUserCache() {

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
