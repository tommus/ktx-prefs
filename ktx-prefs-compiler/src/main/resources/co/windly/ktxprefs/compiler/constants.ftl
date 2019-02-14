package ${package};

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
class ${constantsClassName} {

  companion object {
<#list prefList as pref>

    //region ${pref.fieldName?cap_first}

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    val KEY_${pref.fieldNameUpperCase} = "${pref.prefName}";

    /**
     * Default value stored for the shared preference under "${pref.prefName}" key.
     */
    val DEFAULT_${pref.fieldNameUpperCase} = ${pref.defaultValue};

    //endregion

</#list>
  }
}
