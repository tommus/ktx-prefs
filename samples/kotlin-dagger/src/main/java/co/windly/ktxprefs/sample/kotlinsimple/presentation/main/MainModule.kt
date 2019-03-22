package co.windly.ktxprefs.sample.kotlinsimple.presentation.main

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

  //region Contribution

  @ContributesAndroidInjector
  abstract fun contributeMainAndroidInjector(): MainActivity

  //endregion
}
