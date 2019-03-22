package co.windly.ktxprefs.sample.kotlinsimple.application

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KotlinDagger : Application(), ApplicationComponent.ComponentProvider, HasActivityInjector {

  //region Component

  override lateinit var applicationComponent: ApplicationComponent

  //endregion

  //region Injector

  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  override fun activityInjector(): AndroidInjector<Activity> =
    activityInjector

  //endregion

  //region Lifecycle

  override fun onCreate() {
    super.onCreate()

    // Initialize dependency graph.
    initializeDependencyInjection()
  }

  //endregion

  //region Dependency Injection

  private fun initializeDependencyInjection() {

    // Initialize application component.
    applicationComponent = DaggerApplicationComponent.builder()
      .application(this)
      .build()
    applicationComponent.inject(this)
  }

  //endregion
}
