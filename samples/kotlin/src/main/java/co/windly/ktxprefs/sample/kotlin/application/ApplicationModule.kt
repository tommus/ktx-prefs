package co.windly.ktxprefs.sample.kotlin.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

  //region Context

  @Provides
  @Singleton
  internal fun provideApplicationContext(application: SampleApplication): Context =
    application

  //endregion
}
