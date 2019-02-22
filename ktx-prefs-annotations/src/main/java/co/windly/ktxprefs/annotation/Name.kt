package co.windly.ktxprefs.annotation

/**
 * Allows to specify a shared preference name for the field.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FIELD)
annotation class Name(

  /**
   * Name of the shared preference.
   *
   * @return Name of the shared preference.
   */
  val value: String
)
