package ${package};

import java.util.Set;

import android.content.SharedPreferences;

import co.windly.ktxprefs.runtime.EditorWrapper;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ${editorWrapperClassName}(wrapped: SharedPreferences.Editor) : EditorWrapper(wrapped) {
<#list prefList as pref>

  //region ${pref.fieldName?cap_first}

  <#if pref.comment??>
  /**
   * ${pref.comment?trim}
   */
  </#if><#t>
  fun put${pref.fieldName?cap_first}(${pref.fieldName}: ${pref.type.simpleName}): ${editorWrapperClassName} =
    when(${pref.fieldName} == null) {
      true -> remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
      false -> put${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.fieldName})
    }.let { this }

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
  fun remove${pref.fieldName?cap_first}(): ${editorWrapperClassName} =
    remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase})
      .let { this }

  //endregion
</#list>
}
