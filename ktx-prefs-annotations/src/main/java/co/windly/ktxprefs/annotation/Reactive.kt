package co.windly.ktxprefs.annotation

/**
 * Allows to customize how reactive extensions will work.
 * <p>
 * For example, you can enable / disable generation of extension methods.
 * <p>
 * This annotation can be applied to either:
 * <dl>
 *  <dt>Class:</dt>
 *  <dd>At this level, the configuration will be applied to all fields (shared preferences) in given class unless the
 *   configuration will overridden on field level.</dd>
 *  <dt>Property:</dt>
 *  <dd>At this level, the configuration will be applied only to given field. It overrides behavior applied at class
 *   level</dd>
 * </dl>
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class Reactive(

  /**
   * Disable / enable generation of extenion method.
   *
   * @return information whether extension methods should be generated or not.
   */
  val value: Boolean = true,

  /**
   * Changes if update of given preference should be emitted always or only if they were
   * unique.
   *
   * @return information when updates of given preference should be emitted.
   */
  val distinctUntilChanged: Boolean = true
)
