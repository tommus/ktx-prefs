package co.windly.kotlinxprefs.annotations

@Target(AnnotationTarget.FIELD)
annotation class DefaultStringSet(

  vararg val value: String
)
