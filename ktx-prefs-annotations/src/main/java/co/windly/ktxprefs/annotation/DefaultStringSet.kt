package co.windly.kotlinxprefs.annotation

@Target(AnnotationTarget.FIELD)
annotation class DefaultStringSet(

  vararg val value: String
)
