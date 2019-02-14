package co.windly.kotlinxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FIELD)
annotation class Name(

  val value: String
)
