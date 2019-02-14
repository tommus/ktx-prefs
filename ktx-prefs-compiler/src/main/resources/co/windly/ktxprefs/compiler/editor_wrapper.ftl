package ${package};

import java.util.Set;

import android.content.SharedPreferences;

import co.windly.androidxprefs.EditorWrapper;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
public class ${editorWrapperClassName} extends EditorWrapper {

    //region Constructor

    public ${editorWrapperClassName}(SharedPreferences.Editor wrapped) {
        super(wrapped);
    }

    //endregion

    //region Shared Preferences
<#list prefList as pref>

    //region ${pref.fieldName?cap_first}

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {

        // Truncate shared preference in case if null value has been passed.
        if (${pref.fieldName} == null) {
            remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
        }

        // Otherwise update shared preference.
        else {
            put${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.fieldName});
        }

        // Return an object instance to allow method chaining.
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} set${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {

        // This method is basically an alias for `put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName})` method.
        return put${pref.fieldName?cap_first}(${pref.fieldName});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} remove${pref.fieldName?cap_first}() {

        // Truncate shared preference.
        remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase});

        // Return an object instance to allow method chaining.
        return this;
    }

    //endregion
</#list>

    //endregion
}