package co.windly.kotlinxprefs.annotation

@Target(AnnotationTarget.FIELD)
annotation class DefaultString(

  val value: String
)
