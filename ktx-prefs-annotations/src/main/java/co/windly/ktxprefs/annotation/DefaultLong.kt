package co.windly.ktxprefs.annotation

/**
 * Indicates a wrapper methods for given Long preference should be generated.
 * <p>
 * One can configure what default value will be used in case if given property was
 * not set previously,
 */
@Target(AnnotationTarget.FIELD)
annotation class DefaultLong(

  /**
   * The default value for given Long preference.
   * @return the default value for given Long preference
   */
  val value: Long
)
