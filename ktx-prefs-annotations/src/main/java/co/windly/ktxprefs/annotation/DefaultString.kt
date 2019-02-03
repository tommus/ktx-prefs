package co.windly.kotlinxprefs.annotations

@Target(AnnotationTarget.FIELD)
annotation class DefaultString(

  val value: String
)
