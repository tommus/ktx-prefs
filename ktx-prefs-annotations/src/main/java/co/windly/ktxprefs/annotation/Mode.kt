package co.windly.ktxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Mode(

  val value: Int
)
