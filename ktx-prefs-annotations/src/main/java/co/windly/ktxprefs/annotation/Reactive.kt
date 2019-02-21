package co.windly.ktxprefs.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class Reactive(

  val value: Boolean = true,

  val distinctUntilChanged: Boolean = true
)
