package co.windly.ktxprefs.annotation

/**
 * Indicates a wrapper methods for given Boolean preference should be generated.
 * <p>
 * One can configure what default value will be used in case if given property was
 * not set previously,
 */
@Target(AnnotationTarget.FIELD)
annotation class DefaultBoolean(

  /**
   * The default value for given Boolean preference.
   * @return the default value for given Boolean preference
   */
  val value: Boolean
)
