package co.windly.ktxprefs.sample.kotlinsimple.persistence.shared.cache

import android.content.Context

class UserCache(context: Context) :
  UserCacheProviderPrefs(context.requireUserCacheProvider())
