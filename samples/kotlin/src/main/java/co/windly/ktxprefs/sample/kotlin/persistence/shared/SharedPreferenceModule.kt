package co.windly.ktxprefs.sample.kotlin.persistence.shared

import android.content.Context
import co.windly.ktxprefs.sample.kotlin.persistence.shared.cache.UserCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

  //region Cache

  @Provides
  @Singleton
  internal fun provideUserCache(context: Context): UserCache =
    UserCache(context)

  //endregion
}
