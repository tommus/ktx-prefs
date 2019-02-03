package co.windly.ktxprefs.runtime

import android.content.SharedPreferences

@Suppress("unused")
open class EditorWrapper(private val wrapped: SharedPreferences.Editor) : SharedPreferences.Editor {

  //region Boolean

  override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor =
    wrapped.putBoolean(key, value).let { this }

  //endregion

  //region Int

  override fun putInt(key: String?, value: Int): SharedPreferences.Editor =
    wrapped.putInt(key, value).let { this }

  //endregion

  //region Long

  override fun putLong(key: String?, value: Long): SharedPreferences.Editor =
    wrapped.putLong(key, value).let { this }

  //endregion

  //region Float

  override fun putFloat(key: String?, value: Float): SharedPreferences.Editor =
    wrapped.putFloat(key, value).let { this }

  //endregion

  //region String

  override fun putString(key: String?, value: String?): SharedPreferences.Editor =
    wrapped.putString(key, value).let { this }

  //endregion

  //region String Set

  override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor =
    wrapped.putStringSet(key, values).let { this }

  //endregion

  //region Remove

  override fun remove(key: String?): SharedPreferences.Editor =
    wrapped.remove(key).let { this }

  //endregion

  //region Clear

  override fun clear(): SharedPreferences.Editor =
    wrapped.clear().let { this }

  //endregion

  //region Commit

  override fun commit(): Boolean =
    wrapped.commit()

  //endregion

  //region Apply

  override fun apply() {
    wrapped.apply()
  }

  //endregion
}
