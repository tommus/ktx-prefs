package co.windly.ktxprefs.annotation

/**
 * Allows specific customization about the entire shared preference file associated with this class.
 * <p>
 * For example, you can specify a file name.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Prefs(

  /**
   * Shared Preference file name.
   *
   * @return name of the file that was used for particular Shared Preferences.
   */
  val value: String = "",

  /**
   * Alias for value property.
   */
  val fileName: String = ""
)
