package co.windly.ktxprefs.sample.kotlin.presentation.reactive

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ReactiveModule {

  //region Contribution

  @ContributesAndroidInjector
  abstract fun contributeAndroidInjector(): ReactiveActivity

  //endregion
}
