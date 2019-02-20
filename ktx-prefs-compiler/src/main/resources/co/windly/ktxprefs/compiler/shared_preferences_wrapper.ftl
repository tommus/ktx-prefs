package ${package};

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import co.windly.ktxprefs.runtime.SharedPreferencesWrapper;

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

    //endregion

    //region Clear

    override fun clear() {
      edit().clear()
    }

    //endregion

<#list prefList as pref>
    //region ${pref.fieldName?cap_first}
    <#if pref.type == "BOOLEAN">

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    fun get${pref.fieldName?cap_first}(): ${pref.type.simpleName} =
      is${pref.fieldName?cap_first}();
    </#if>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    fun <#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}(): ${pref.type.simpleName} =
      when(!contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase})) {
        true -> ${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase}
        false -> get${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.type.defaultValue})
      }

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
    fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
      edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply().let { this }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    fun set${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${prefWrapperClassName} =
      put${pref.fieldName?cap_first}(${pref.fieldName})

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    fun remove${pref.fieldName?cap_first}(): ${prefWrapperClassName} =
        edit().remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}).apply().let { this }

    //endregion

</#list>
}
