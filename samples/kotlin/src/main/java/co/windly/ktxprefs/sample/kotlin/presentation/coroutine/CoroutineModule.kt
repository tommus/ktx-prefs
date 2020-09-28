package co.windly.ktxprefs.sample.kotlin.presentation.coroutine

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CoroutineModule {

  //region Contribution

  @ContributesAndroidInjector
  abstract fun contributeAndroidInjector(): CoroutineActivity

  //endregion
}
