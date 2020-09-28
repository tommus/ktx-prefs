package co.windly.ktxprefs.annotation

/**
 * Allows to customize whether methods while being generated will
 * support coroutine's suspend feature.
 * <p>
 * For example, you can enable / disable generation of suspend
 * variants of methods.
 * <p>
 * This annotation can be applied to either:
 * <dl>
 *  <dt>Class:</dt>
 *  <dd>At this level, the configuration will be applied to all
 *  fields (shared preferences) in given class unless the
 *  configuration will be overridden at field level.</dd>
 *  <dt>Property:</dt>
 *  <dd>At this level, the configuration will be applied only to
 *  given field. It overrides behavior applied at class level.</dd>
 * </dl>
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class Suspend(

  /**
   * Disable / enable generation of suspend method alternatives.
   *
   * @return information whether suspend method alternatives should
   * be generated (or not).
   */
  val value: Boolean = true
)
