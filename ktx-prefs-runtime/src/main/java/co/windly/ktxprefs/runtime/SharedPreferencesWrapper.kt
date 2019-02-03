package co.windly.ktxprefs.runtime

import android.content.SharedPreferences

@Suppress("unused")
open class SharedPreferencesWrapper(private val wrapped: SharedPreferences) : SharedPreferences {

  //region Contains

  override fun contains(key: String?): Boolean =
    wrapped.contains(key)

  //endregion

  //region All

  override fun getAll(): MutableMap<String, *> =
    wrapped.all

  //endregion

  //region Boolean

  override fun getBoolean(key: String?, defValue: Boolean): Boolean =
    wrapped.getBoolean(key, defValue)

  //endregion

  //region Int

  override fun getInt(key: String?, defValue: Int): Int =
    wrapped.getInt(key, defValue)

  //endregion

  //region Long

  override fun getLong(key: String?, defValue: Long): Long =
    wrapped.getLong(key, defValue)

  //endregion

  //region Float

  override fun getFloat(key: String?, defValue: Float): Float =
    wrapped.getFloat(key, defValue)

  //endregion

  //region String

  override fun getString(key: String?, defValue: String?): String =
    wrapped.getString(key, defValue) ?: ""

  //endregion

  //region String Set

  override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> =
    wrapped.getStringSet(key, defValues) ?: mutableSetOf()

  //endregion

  //region Edit

  override fun edit(): SharedPreferences.Editor =
    wrapped.edit()

  //endregion

  //region Clear

  open fun clear() {
    wrapped.edit().clear().apply()
  }

  //endregion

  //region Listener

  override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
    wrapped.registerOnSharedPreferenceChangeListener(listener)
  }

  override fun unregisterOnSharedPreferenceChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
    wrapped.unregisterOnSharedPreferenceChangeListener(listener)
  }

  //endregion
}
