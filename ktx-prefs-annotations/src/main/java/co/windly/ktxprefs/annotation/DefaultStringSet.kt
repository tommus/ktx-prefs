package co.windly.ktxprefs.annotation

@Target(AnnotationTarget.FIELD)
annotation class DefaultStringSet(

  vararg val value: String
)
