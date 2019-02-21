package co.windly.ktxprefs.compiler

import org.apache.commons.lang3.builder.ToStringBuilder

class Pref internal constructor(

  //region Field Name

  val fieldName: String,

  //endregion

  //region Preference Name

  val prefName: String?,

  //endregion

  //region Type

  val type: PrefType,

  //endregion

  //region Default Value

  val defaultValue: String?,

  //endregion

  //region Comment

  val comment: String?,

  //endregion

  //region Enable Reactive

  val enableReactive: Boolean = true,

  //endregion

  //region Distinct Until Changed

  val distinctUntilChanged: Boolean = true

  //endregion

) {

  //region Field Name

  val fieldNameUpperCase: String
    get() = fieldName.replace("([A-Z]+)".toRegex(), "\\_$1").toUpperCase()

  //endregion

  //region String

  override fun toString(): String {
    return ToStringBuilder(this)
      .append("fieldName", fieldName)
      .append("prefName", prefName)
      .append("type", type)
      .append("defaultValue", defaultValue)
      .append("comment", comment)
      .append("enableReactive", enableReactive)
      .append("distinctUntilChanged", distinctUntilChanged)
      .toString()
  }

  //endregion
}
