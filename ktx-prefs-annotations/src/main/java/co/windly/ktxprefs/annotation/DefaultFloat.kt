package co.windly.ktxprefs.annotation

/**
 * Indicates a wrapper methods for given Float preference should be
 * generated.
 * <p>
 * One can configure what default value will be used in case if given
 * property was not set previously.
 */
@Target(AnnotationTarget.FIELD)
annotation class DefaultFloat(

  /**
   * The default value for given Float preference.
   * @return the default value for given Float preference
   */
  val value: Float
)
