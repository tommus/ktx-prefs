package co.windly.ktxprefs.sample.kotlin.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class SampleApplication : DaggerApplication() {

  //region Dispatcher

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
    DaggerApplicationComponent
      .builder()
      .application(this)
      .build()

  //endregion
}
