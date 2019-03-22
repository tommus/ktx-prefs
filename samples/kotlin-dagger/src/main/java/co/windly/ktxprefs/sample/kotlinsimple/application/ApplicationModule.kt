package co.windly.ktxprefs.sample.kotlinsimple.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

  //region Context

  @Provides
  @Singleton
  internal fun provideApplicationContext(application: KotlinDagger): Context =
    application

  //endregion
}
