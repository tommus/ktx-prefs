package co.windly.ktxprefs.sample.kotlinsimple.presentation.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import co.windly.ktxprefs.sample.kotlinsimple.R.layout
import co.windly.ktxprefs.sample.kotlinsimple.persistence.shared.cache.UserCache
import dagger.android.AndroidInjection
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

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

    // Inject dependencies.
    AndroidInjection.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

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

  @Inject
  lateinit var cache: UserCache

  private fun initializeUserCache() {

    // Put a single value.
    cache
      .putRxId(1L)
      .subscribe()
      .addTo(disposables)

    // Put several values in chained stream.
    Completable
      .mergeArrayDelayError(
        cache.putRxFirstName("John"),
        cache.putRxLastName("Snow"),
        cache.putRxPassword("WinterIsComing"),
        cache.putRxActive(true)
      )
      .subscribe()
      .addTo(disposables)

    // Check if a value is set.
    cache
      .containsRxFirstName()
      .subscribe { flag -> Log.d(TAG, "First name is set: $flag.") }
      .addTo(disposables)

    // Access preferences one by one.
    cache
      .getRxId()
      .subscribe { id -> Log.d(TAG, "id -> $id.") }
      .addTo(disposables)

    cache
      .getRxFirstName()
      .subscribe { firstName ->
        Log.d(
          TAG, "first name -> $firstName.")
      }
      .addTo(disposables)

    cache
      .getRxLastName()
      .subscribe { lastName ->
        Log.d(
          TAG, "last name -> $lastName.")
      }
      .addTo(disposables)

    cache
      .getRxPassword()
      .subscribe { password ->
        Log.d(
          TAG, "password -> $password.")
      }
      .addTo(disposables)

    cache
      .getRxActive()
      .subscribe { active -> Log.d(TAG, "active -> $active.") }
      .addTo(disposables)

    // Remove a value.
    cache
      .removeRxFirstName()
      .subscribe()
      .addTo(disposables)

    // Clear all preferences.
    cache
      .clearRx()
      .subscribe()
      .addTo(disposables)
  }

  //endregion

  //region First Name

  private fun observeFirstName() {

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
