package co.windly.ktxprefs.compiler

import java.util.Arrays
import javax.lang.model.type.TypeMirror

enum class PrefType constructor(

  //region Qualified Name

  val qualifiedName: String,

  //endregion

  //region Simple Name

  val simpleName: String,

  //endregion

  //region Non Null Simple Name

  val nonNullSimpleName: String,

  //endregion

  //region Method Name

  val methodName: String,

  //endregion

  //region Default Value

  val defaultValue: String

  //endregion

) {

  //region Supported Data Types

  BOOLEAN(
    qualifiedName = Boolean::class.java.name,
    simpleName = "Boolean?",
    nonNullSimpleName = "Boolean",
    methodName = "Boolean",
    defaultValue = "false"
  ),

  FLOAT(
    qualifiedName = Float::class.java.name,
    simpleName = "Float?",
    nonNullSimpleName = "Float",
    methodName = "Float",
    defaultValue = "0.0f"
  ),

  INTEGER(
    qualifiedName = Int::class.java.name,
    simpleName = "Int?",
    nonNullSimpleName = "Int",
    methodName = "Int",
    defaultValue = "0"
  ),

  LONG(
    qualifiedName = Long::class.java.name,
    simpleName = "Long?",
    nonNullSimpleName = "Long",
    methodName = "Long",
    defaultValue = "0L"
  ),

  STRING(
    qualifiedName = String::class.java.name,
    simpleName = "String?",
    nonNullSimpleName = "String",
    methodName = "String",
    defaultValue = "\"\""
  ),

  STRING_SET(
    qualifiedName = "kotlin.collections.Set<kotlin.String>",
    simpleName = "Set<String>?",
    nonNullSimpleName = "Set<String>",
    methodName = "StringSet",
    defaultValue = "kotlin.collections.emptySet<kotlin.String>()"
  );

  //endregion

  //region Compatibility

  fun isCompatible(type: TypeMirror): Boolean =
    qualifiedName == type.toString()

  //endregion

  //region Companion

  companion object {

    //region From

    fun from(fieldType: TypeMirror): PrefType {

      // Retrieve qualified name.
      val qualifiedName = fieldType.toString()

      // Retrieve type for given qualified type.
      val type = Arrays
        .stream(values())
        .filter { it.qualifiedName == qualifiedName }
        .findFirst()

      // Return type for given type.
      if (type.isPresent) {
        return type.get()
      }

      // Throw an error in case if qualified type is not supported.
      throw IllegalArgumentException("Unsupported type: $qualifiedName.")
    }

    //endregion

    //region Allowed Type

    fun isAllowedType(fieldType: TypeMirror): Boolean {

      // Retrieve qualified name.
      val qualifiedName = fieldType.toString()

      // Return an information whether given type is supported.
      return Arrays
        .stream(values())
        .anyMatch { it.qualifiedName == qualifiedName }
    }

    //endregion
  }

  //endregion
}
