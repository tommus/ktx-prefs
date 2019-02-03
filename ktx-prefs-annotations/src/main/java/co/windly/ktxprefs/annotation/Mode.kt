package co.windly.kotlinxprefs.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Mode(

  val value: Int
)
