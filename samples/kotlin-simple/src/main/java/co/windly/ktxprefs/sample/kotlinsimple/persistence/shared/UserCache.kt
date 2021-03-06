package co.windly.ktxprefs.sample.kotlinsimple.persistence.shared

import co.windly.ktxprefs.annotation.DefaultBoolean
import co.windly.ktxprefs.annotation.DefaultFloat
import co.windly.ktxprefs.annotation.DefaultInt
import co.windly.ktxprefs.annotation.DefaultLong
import co.windly.ktxprefs.annotation.DefaultString
import co.windly.ktxprefs.annotation.Prefs
import co.windly.ktxprefs.annotation.Reactive

@Prefs(value = "UserCachePreferences")
@Reactive(value = false)
class UserCache(

  //region Id

  @DefaultLong(value = 0L)
  internal val id: Long,

  //endregion

  //region Name

  @DefaultString(value = "")
  internal val firstName: String,

  @DefaultString(value = "")
  internal val lastName: String,

  @DefaultString(value = "")
  internal val password: String,

  //endregion

  //region Active

  @DefaultBoolean(value = false)
  internal val active: Boolean,

  //endregion

  //region Age

  @DefaultInt(value = 0)
  internal val age: Int,

  //endregion

  //region Height

  @DefaultFloat(value = 0.0f)
  internal val height: Float

  //endregion
)
