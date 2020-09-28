package co.windly.ktxprefs.sample.kotlin.presentation.simple

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SimpleModule {

  //region Contribution

  @ContributesAndroidInjector
  abstract fun contributeAndroidInjector(): SimpleActivity

  //endregion
}
