package co.windly.ktxprefs.annotation

/**
 * Indicates a wrapper methods for given Int preference should be
 * generated.
 * <p>
 * One can configure what default value will be used in case if given
 * property was not set previously.
 */
@Target(AnnotationTarget.FIELD)
annotation class DefaultInt(

  /**
   * The default value for given Int preference.
   * @return the default value for given Int preference
   */
  val value: Int
)
