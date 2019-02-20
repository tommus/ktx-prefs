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
      .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
    </#list>

  //endregion

<#list prefList as pref>

  //region ${pref.fieldName?cap_first}

  internal lateinit var ${pref.fieldName}Subject: BehaviorSubject<${pref.type.nonNullSimpleName}>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${editorWrapperClassName} =
    when (${pref.fieldName} == null) {
      true ->
        remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
          .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
      false ->
        put${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.fieldName})
          .also { ${pref.fieldName}Subject.onNext(${pref.fieldName}) }
    }.let { this }

  <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
  </#if><#t>
  fun putRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    Completable
      .defer { Completable.fromAction { put${pref.fieldName?cap_first}(${pref.fieldName}) } }
      .subscribeOn(Schedulers.io())

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun set${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${editorWrapperClassName} =
    put${pref.fieldName?cap_first}(${pref.fieldName})

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun setRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    putRx${pref.fieldName?cap_first}(${pref.fieldName})

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun remove${pref.fieldName?cap_first}(): ${editorWrapperClassName} =
    remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
      .also { ${pref.fieldName}Subject.onNext(${pref.defaultValue}) }
      .let { this }

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun removeRx${pref.fieldName?cap_first}(): Completable =
    Completable
      .defer { Completable.fromAction { remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}) } }
      .subscribeOn(Schedulers.io())

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun observeRx${pref.fieldName?cap_first}(): Flowable<${pref.type.nonNullSimpleName}> =
    ${pref.fieldName}Subject
      .toFlowable(BackpressureStrategy.LATEST)
      <#if distinctUntilChanged>
      .distinctUntilChanged()
      </#if>

  //endregion
</#list>
}
