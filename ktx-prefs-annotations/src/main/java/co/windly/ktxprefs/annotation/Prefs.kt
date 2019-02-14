package co.windly.kotlinxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Prefs(

  val value: String = "",

  val fileName: String = "",

  val fileMode: Int = -1,

  val disableNullable: Boolean = false
)
