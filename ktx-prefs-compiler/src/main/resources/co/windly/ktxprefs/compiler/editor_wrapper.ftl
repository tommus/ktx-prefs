package ${package};

import android.content.SharedPreferences
import co.windly.ktxprefs.runtime.EditorWrapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ${editorWrapperClassName}(wrapped: SharedPreferences.Editor) : EditorWrapper(wrapped) {

  //region Clear

  override fun clear(): SharedPreferences.Editor =
      super.clear()
          <#list prefList as pref>
          <#if pref.enableReactive>
          .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
          </#if>
          </#list>

  //endregion
<#list prefList as pref>

  //region ${pref.fieldName?cap_first}
  <#if pref.enableReactive>

  internal lateinit var ${pref.fieldName}Subject: BehaviorSubject<${pref.type.nonNullSimpleName}>
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${editorWrapperClassName} =
      when (${pref.fieldName} == null) {
        true ->
          remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
              <#if pref.enableReactive>
              .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
              </#if>
        false ->
          put${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.fieldName})
              <#if pref.enableReactive>
              .also { ${pref.fieldName}Subject.onNext(${pref.fieldName}) }
              </#if>
      }.let { this }
  <#if pref.enableReactive>

  <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
  </#if><#t>
  fun putRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
      Completable
          .fromAction { put${pref.fieldName?cap_first}(${pref.fieldName}).apply() }
          .subscribeOn(Schedulers.io())
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun set${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${editorWrapperClassName} =
      put${pref.fieldName?cap_first}(${pref.fieldName})
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun setRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
      putRx${pref.fieldName?cap_first}(${pref.fieldName})
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun remove${pref.fieldName?cap_first}(): ${editorWrapperClassName} =
      remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
          .apply()
          <#if pref.enableReactive>
          .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
          </#if>
          .let { this }
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun removeRx${pref.fieldName?cap_first}(): Completable =
      Completable
          .fromAction { remove${pref.fieldName?cap_first}().apply() }
          .subscribeOn(Schedulers.io())
  </#if>

  <#if pref.enableReactive>
  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun observeRx${pref.fieldName?cap_first}(): Flowable<${pref.type.nonNullSimpleName}> =
      ${pref.fieldName}Subject
          .toFlowable(BackpressureStrategy.LATEST)
          <#if pref.distinctUntilChanged>
          .distinctUntilChanged()
          </#if>

  </#if>
  //endregion
</#list>
}
