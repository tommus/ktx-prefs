package ${package};

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
<#if !disableNullable>
import androidx.annotation.Nullable;
</#if><#t>

import co.windly.androidxprefs.SharedPreferencesWrapper;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
public class ${prefWrapperClassName} extends SharedPreferencesWrapper {

    //region Singleton

    private static ${prefWrapperClassName} INSTANCE;

    public static ${prefWrapperClassName} get(Context context) {

        // Ensure singleton instance creation.
        if (INSTANCE == null) {
            SharedPreferences wrapped = getWrapped(context);
            INSTANCE = new ${prefWrapperClassName}(wrapped);
        }

        // Return wrapper instance.
        return INSTANCE;
    }

    //endregion

    //region Factory Method

    protected static SharedPreferences getWrapped(Context context) {
<#if fileName??>
        return context
            .getSharedPreferences("${fileName}", ${fileMode});
<#else>
        return PreferenceManager
            .getDefaultSharedPreferences(context);
</#if>
    }

    //endregion

    //region Constructor

    public ${prefWrapperClassName}(SharedPreferences wrapped) {
        super(wrapped);
    }

    //endregion

    //region Edition

    @SuppressLint("CommitPrefEdits")
    public ${editorWrapperClassName} edit() {
        return new ${editorWrapperClassName}(super.edit());
    }

    //endregion

    //region Shared Preferences

<#list prefList as pref>
    //region ${pref.fieldName?cap_first}
    <#if pref.type == "BOOLEAN">

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    <#if !disableNullable && pref.defaultValue == "null">
    @Nullable
    </#if><#t>
    public ${pref.type.simpleName} get${pref.fieldName?cap_first}() {
        return is${pref.fieldName?cap_first}();
    }
    </#if>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    <#if !disableNullable && pref.defaultValue == "null">
    @Nullable
    </#if><#t>
    public ${pref.type.simpleName} <#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}() {

        // In case if no value has been stored - return default one.
        if (!contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase})) {
            return ${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase};
        }

        // Otherwise return stored shared preference.
        return get${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.type.defaultValue});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public boolean contains${pref.fieldName?cap_first}() {

        // Return whether given shared preference has been stored.
        return contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {

        // Edit and immediately persist shared preference.
        edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply();
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} set${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {

        // Edit and immediately persist shared preference.
        return put${pref.fieldName?cap_first}(${pref.fieldName});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} remove${pref.fieldName?cap_first}() {

        // Truncate persisted shared preference.
        edit().remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}).apply();
        return this;
    }

    //endregion

</#list>
    //endregion
}
