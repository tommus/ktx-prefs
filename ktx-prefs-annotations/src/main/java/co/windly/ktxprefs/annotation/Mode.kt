package co.windly.kotlinxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Mode(

  val value: Int
)
