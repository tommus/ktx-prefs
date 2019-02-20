package ${package};

import android.content.Context
import android.content.SharedPreferences
import co.windly.ktxprefs.runtime.SharedPreferencesWrapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ${prefWrapperClassName}(wrapped: SharedPreferences) : SharedPreferencesWrapper(wrapped) {

  //region Companion

  companion object {

    //region Wrapped

    fun get(context: Context): ${prefWrapperClassName} =
      instance ?: synchronized(this) {
        instance ?: ${prefWrapperClassName}(getWrapped(context)).also { instance = it }
      }

    private fun getWrapped(context: Context): SharedPreferences =
      context.getSharedPreferences("${fileName}", 0)

    @Volatile
    private var instance: ${prefWrapperClassName}? = null

    //endregion
  }

  //endregion

  //region Edit

  override fun edit(): UserCacheEditorWrapper =
    UserCacheEditorWrapper(super.edit())
      .also {
        <#list prefList as pref>
        it.${pref.fieldName}Subject = ${pref.fieldName}Subject
        </#list>
      }

  //endregion

  //region Clear

  override fun clear() {
    edit().clear()
  }

  //endregion

<#list prefList as pref>
  //region ${pref.fieldName?cap_first}

  private val ${pref.fieldName}Subject: BehaviorSubject<${pref.type.nonNullSimpleName}> =
    BehaviorSubject.createDefault(${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase})

  <#if pref.type == "BOOLEAN">
  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun get${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    is${pref.fieldName?cap_first}()

  fun getRx${pref.fieldName?cap_first}(): Single<${pref.type.nonNullSimpleName}> =
      Single
        .defer { Single.just(get${pref.fieldName?cap_first}()) }
        .subscribeOn(Schedulers.io())

  </#if>
  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun <#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    when(!contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase})) {
      true -> ${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase}
      false -> get${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.type.defaultValue})
    }

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun <#if pref.type == "BOOLEAN">is<#else>get</#if>Rx${pref.fieldName?cap_first}(): Single<${pref.type.nonNullSimpleName}> =
    Single
      .defer { Single.just(<#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}()) }
      .subscribeOn(Schedulers.io())

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun contains${pref.fieldName?cap_first}(): Boolean =
    contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase});

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun containsRx${pref.fieldName?cap_first}(): Single<Boolean> =
    Single
      .defer { Single.just(contains${pref.fieldName?cap_first}()) }
      .subscribeOn(Schedulers.io())

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply().let { this }

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun putRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    edit().putRx${pref.fieldName?cap_first}(${pref.fieldName})

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun set${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    edit().set${pref.fieldName?cap_first}(${pref.fieldName}).let { this }

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun setRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    edit().setRx${pref.fieldName?cap_first}(${pref.fieldName})

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun remove${pref.fieldName?cap_first}(): ${prefWrapperClassName} =
      edit().remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}).apply().let { this }

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun removeRx${pref.fieldName?cap_first}(): Completable =
    edit().removeRx${pref.fieldName?cap_first}()

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun observeRx${pref.fieldName?cap_first}(): Flowable<${pref.type.nonNullSimpleName}> =
    edit().observeRx${pref.fieldName?cap_first}()

  //endregion

</#list>
}
