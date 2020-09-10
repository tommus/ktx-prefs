package co.windly.ktxprefs.annotation

/**
 * Indicates a wrapper methods for given String preference should be
 * generated.
 * <p>
 * One can configure what default value will be used in case if given
 * property was not set previously.
 */
@Target(AnnotationTarget.FIELD)
annotation class DefaultString(

  /**
   * The default value for given String preference.
   * @return the default value for given String preference
   */
  val value: String
)
