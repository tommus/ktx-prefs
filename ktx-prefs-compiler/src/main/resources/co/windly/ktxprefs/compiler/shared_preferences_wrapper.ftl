package ${package};

import android.content.Context
import android.content.SharedPreferences
import co.windly.ktxprefs.runtime.SharedPreferencesWrapper
<#if enableReactive>
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
</#if>
<#if enableSuspend>
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
</#if>

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
@Suppress("MemberVisibilityCanBePrivate", "unused")
open class ${prefWrapperClassName}(wrapped: SharedPreferences) : SharedPreferencesWrapper(wrapped) {

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

  override fun edit(): ${editorWrapperClassName} =
    ${editorWrapperClassName}(super.edit())
      <#list prefList as pref>
      <#if pref.enableReactive>
      .also { it.${pref.fieldName}Subject = ${pref.fieldName}Subject }
      </#if>
      </#list>

  //endregion

  //region Clear

  override fun clear() {
    edit().clear().apply()
  }
  <#if enableSuspend>

  suspend fun clearSuspended() =
    withContext(Dispatchers.IO) { clear() }
  </#if>
  <#if enableReactive>

  fun clearRx(): Completable =
    Completable
      .defer { Completable.fromAction { clear() } }
      .subscribeOn(Schedulers.io())
  </#if>

  //endregion
<#list prefList as pref>

  //region ${pref.fieldName?cap_first}
  <#if pref.enableReactive>

  private val ${pref.fieldName}Subject: BehaviorSubject<${pref.type.nonNullSimpleName}> =
    BehaviorSubject.createDefault(get${pref.fieldName?cap_first}())
  </#if>

  <#if pref.type == "BOOLEAN">
  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun get${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    is${pref.fieldName?cap_first}()
  <#if pref.enableSuspend>

  suspend fun getSuspended${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    withContext(Dispatchers.IO) { get${pref.fieldName?cap_first}() }
  </#if>
  <#if pref.enableReactive>

  fun getRx${pref.fieldName?cap_first}(): Single<${pref.type.nonNullSimpleName}> =
    Single
      .defer { Single.just(get${pref.fieldName?cap_first}()) }
      .subscribeOn(Schedulers.io())
  </#if>

  </#if>
  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun <#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    when (!contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase})) {
      true -> ${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase}
      false -> get${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.type.defaultValue})
    }
  <#if pref.enableSuspend>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  suspend fun <#if pref.type == "BOOLEAN">is<#else>get</#if>Suspended${pref.fieldName?cap_first}(): ${pref.type.nonNullSimpleName} =
    withContext(Dispatchers.IO) { get${pref.fieldName?cap_first}() }
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun <#if pref.type == "BOOLEAN">is<#else>get</#if>Rx${pref.fieldName?cap_first}(): Single<${pref.type.nonNullSimpleName}> =
    Single
      .defer { Single.just(<#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}()) }
      .subscribeOn(Schedulers.io())
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun contains${pref.fieldName?cap_first}(): Boolean =
    contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
  <#if pref.enableSuspend>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  suspend fun containsSuspended${pref.fieldName?cap_first}(): Boolean =
    withContext(Dispatchers.IO) { contains${pref.fieldName?cap_first}() }
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun containsRx${pref.fieldName?cap_first}(): Single<Boolean> =
    Single
      .defer { Single.just(contains${pref.fieldName?cap_first}()) }
      .subscribeOn(Schedulers.io())
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply().let { this }
  <#if pref.enableSuspend>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  suspend fun putSuspended${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    withContext(Dispatchers.IO) { put${pref.fieldName?cap_first}(${pref.fieldName}) }
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun putRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    edit().putRx${pref.fieldName?cap_first}(${pref.fieldName})
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun set${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    edit().set${pref.fieldName?cap_first}(${pref.fieldName}).apply().let { this }
  <#if pref.enableSuspend>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  suspend fun setSuspended${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
    withContext(Dispatchers.IO) { set${pref.fieldName?cap_first}(${pref.fieldName}) }
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun setRx${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): Completable =
    edit().setRx${pref.fieldName?cap_first}(${pref.fieldName})
  </#if>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun remove${pref.fieldName?cap_first}(): ${prefWrapperClassName} =
    edit().remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}).apply().let { this }
  <#if pref.enableSuspend>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  suspend fun removeSuspended${pref.fieldName?cap_first}(): ${prefWrapperClassName} =
    withContext(Dispatchers.IO) { remove${pref.fieldName?cap_first}() }
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun removeRx${pref.fieldName?cap_first}(): Completable =
    edit().removeRx${pref.fieldName?cap_first}()
  </#if>
  <#if pref.enableSuspend>

  // TODO: Implement LiveData stream to expose shared preference.
  </#if>
  <#if pref.enableReactive>

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun observeRx${pref.fieldName?cap_first}(): Flowable<${pref.type.nonNullSimpleName}> =
    edit().observeRx${pref.fieldName?cap_first}()
  </#if>

  //endregion
</#list>
}
