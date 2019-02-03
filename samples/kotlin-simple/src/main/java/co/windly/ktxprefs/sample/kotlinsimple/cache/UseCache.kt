package co.windly.ktxprefs.sample.kotlinsimple.cache

import android.content.Context.MODE_PRIVATE
import co.windly.kotlinxprefs.annotations.DefaultBoolean
import co.windly.kotlinxprefs.annotations.DefaultFloat
import co.windly.kotlinxprefs.annotations.DefaultInt
import co.windly.kotlinxprefs.annotations.DefaultLong
import co.windly.kotlinxprefs.annotations.DefaultString
import co.windly.kotlinxprefs.annotations.DefaultStringSet
import co.windly.kotlinxprefs.annotations.Prefs

@Prefs(
  value = "UserCachePreferences",
  fileMode = MODE_PRIVATE
)
class UseCache(

  //region Id

  @DefaultLong(value = 0L)
  internal val id: Long,

  //endregion

  //region Name

  @DefaultString(value = "")
  internal val firstName: String,

  @DefaultString(value = "")
  internal val lastName: String,

  //endregion

  //region Active

  @DefaultBoolean(value = true)
  internal val active: Boolean,

  //endregion

  //region Age

  @DefaultInt(value = 0)
  internal val age: Int,

  //endregion

  //region Height

  @DefaultFloat(value = 0.0f)
  internal val height: Float,

  //endregion

  //region Address

  @DefaultStringSet(value = ["", "", "", ""])
  internal val address: Set<String>

  //endregion
)
