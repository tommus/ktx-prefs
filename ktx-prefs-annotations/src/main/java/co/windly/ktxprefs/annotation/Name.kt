package co.windly.ktxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FIELD)
annotation class Name(

  val value: String
)
