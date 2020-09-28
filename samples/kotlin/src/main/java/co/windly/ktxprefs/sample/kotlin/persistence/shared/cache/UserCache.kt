package co.windly.ktxprefs.sample.kotlin.persistence.shared.cache

import android.content.Context

class UserCache(context: Context) :
  UserCacheProviderPrefs(context.requireUserCacheProvider())
