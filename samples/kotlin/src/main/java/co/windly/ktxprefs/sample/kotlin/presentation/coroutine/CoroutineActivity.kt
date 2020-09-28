package co.windly.ktxprefs.sample.kotlin.presentation.coroutine

import android.os.Bundle
import android.util.Log
import co.windly.ktxprefs.sample.kotlin.R
import co.windly.ktxprefs.sample.kotlin.persistence.shared.cache.UserCache
import dagger.android.AndroidInjection
import dagger.android.DaggerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CoroutineActivity : DaggerActivity() {

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

  override fun onDestroy() {
    super.onDestroy()

    // Cancel all coroutines when activity is about to be destroyed.
    activityScope.cancel()
  }

  //endregion

  //region Initialize Cache

  @Inject
  lateinit var cache: UserCache

  private val activityScope: CoroutineScope =
    CoroutineScope(Job())

  private fun initializeUserCache() {

    // Put a single value (apply() is automatically called) in a blocking manner.
    runBlocking { cache.putSuspendedId(1L) }

    // Schedule update within a job.
    val job = activityScope.launch {

      // Put several values at once.
      cache
        .putSuspendedFirstName("John")
        .putSuspendedLastName("Snow")
        .putSuspendedPassword("WinterIsComing")
        .putSuspendedActive(true)

      // Retrieve a shared preference value from suspended method.
      val active =
        cache.containsSuspendedActive()

      // Check if a value is set.
      if (active) {
        Log.d(TAG, "First name is set.")
      }

      // Access preferences one by one.
      with(cache) {
        Log.d(TAG, "id -> ${getSuspendedId()}.")
        Log.d(TAG, "first name -> ${getSuspendedFirstName()}.")
        Log.d(TAG, "last name -> ${getSuspendedLastName()}.")
        Log.d(TAG, "password -> ${getSuspendedPassword()}.")
        Log.d(TAG, "active -> ${isSuspendedActive()}.")
      }
    }

    // Wait until job will be completed before the app will continue.
    runBlocking { job.join() }

    // Access all preferences.
    Log.d(TAG, "cache -> ${cache.all}.")

    // Remove a value.
    runBlocking { cache.removeSuspendedFirstName() }

    // Clear all preferences.
    runBlocking { cache.clearSuspended() }
  }

  //endregion
}
