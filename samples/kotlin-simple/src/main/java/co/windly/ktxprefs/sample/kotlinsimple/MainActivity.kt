package co.windly.ktxprefs.sample.kotlinsimple

import android.app.Activity
import android.os.Bundle
import android.util.Log
import co.windly.ktxprefs.sample.kotlinsimple.cache.requireUserCache
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainActivity : Activity() {

  //region Companion

  companion object {
    const val TAG = "MainActivity"
  }

  //endregion

  //region Disposables

  private val disposables: CompositeDisposable
    by lazy { CompositeDisposable() }

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Subscribe to cache changes.
    observeFirstName()
    observeLastName()
    observePassword()
    observeActive()

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
    Log.d(TAG, "id -> " + cache.getId())
    Log.d(TAG, "first name -> " + cache.getFirstName())
    Log.d(TAG, "last name -> " + cache.getLastName())
    Log.d(TAG, "password -> " + cache.getPassword())
    Log.d(TAG, "active -> " + cache.isActive())

    // Access all preferences.
    Log.d(TAG, "cache -> " + cache.getAll())

    // Remove a value.
    cache.removeFirstName()

    // Clear all preferences.
    cache.clear()
  }

  //endregion

  //region First Name

  private fun observeFirstName() {

    // Get access to shared preferences wrapper.
    val cache = requireUserCache()

    // Subscribe to first name changes.
    cache
      .observeRxFirstName()
      .subscribe(
        { handleObserveRxFirstNameSuccess(it) },
        { handleObserveRxFirstNameError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxFirstNameSuccess(firstName: String) {

    // Log the fact.
    Log.d(TAG, "First name changed: $firstName.")
  }

  private fun handleObserveRxFirstNameError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing first name.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Last Name

  private fun observeLastName() {

    // Get access to shared preferences wrapper.
    val cache = requireUserCache()

    // Subscribe to last name changes.
    cache
      .observeRxLastName()
      .subscribe(
        { handleObserveRxLastNameSuccess(it) },
        { handleObserveRxLastNameError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxLastNameSuccess(lastName: String) {

    // Log the fact.
    Log.d(TAG, "Last name changed: $lastName.")
  }

  private fun handleObserveRxLastNameError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing last name.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Password

  private fun observePassword() {

    // Get access to shared preferences wrapper.
    val cache = requireUserCache()

    // Subscribe to password changes.
    cache
      .observeRxPassword()
      .subscribe(
        { handleObserveRxPasswordSuccess(it) },
        { handleObserveRxPasswordError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxPasswordSuccess(password: String) {

    // Log the fact.
    Log.d(TAG, "Password changed: $password.")
  }

  private fun handleObserveRxPasswordError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing password.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Active

  private fun observeActive() {

    // Get access to shared preferences wrapper.
    val cache = requireUserCache()

    // Subscribe to active changes.
    cache
      .observeRxActive()
      .subscribe(
        { handleObserveRxActiveSuccess(it) },
        { handleObserveRxActiveError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxActiveSuccess(active: Boolean) {

    // Log the fact.
    Log.d(TAG, "Active changed: $active.")
  }

  private fun handleObserveRxActiveError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing active.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion
}
