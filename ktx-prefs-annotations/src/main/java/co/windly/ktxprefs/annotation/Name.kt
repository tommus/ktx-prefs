package co.windly.kotlinxprefs.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FIELD)
annotation class Name(

  val value: String
)
