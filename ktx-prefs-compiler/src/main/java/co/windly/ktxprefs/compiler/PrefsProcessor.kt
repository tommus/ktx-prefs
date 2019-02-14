package co.windly.ktxprefs.compiler

import co.windly.kotlinxprefs.annotation.DefaultBoolean
import co.windly.kotlinxprefs.annotation.DefaultFloat
import co.windly.kotlinxprefs.annotation.DefaultInt
import co.windly.kotlinxprefs.annotation.DefaultLong
import co.windly.kotlinxprefs.annotation.DefaultString
import co.windly.kotlinxprefs.annotation.DefaultStringSet
import co.windly.kotlinxprefs.annotation.Name
import co.windly.ktxprefs.compiler.PrefType.BOOLEAN
import co.windly.ktxprefs.compiler.PrefType.FLOAT
import co.windly.ktxprefs.compiler.PrefType.INTEGER
import co.windly.ktxprefs.compiler.PrefType.LONG
import co.windly.ktxprefs.compiler.PrefType.STRING
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.BASE_PACKAGE_PATH
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MAJOR_VERSION
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MICRO_VERSION
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MINOR_VERSION
import freemarker.template.Configuration
import freemarker.template.Version
import org.apache.commons.lang3.StringEscapeUtils.escapeJava
import org.apache.commons.lang3.StringUtils.wrap
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion.RELEASE_8
import javax.lang.model.element.Modifier
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.ElementFilter
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.NOTE

@SupportedAnnotationTypes(
  value = [
    "co.windly.ktxprefs.annotation.DefaultBoolean",
    "co.windly.ktxprefs.annotation.DefaultFloat",
    "co.windly.ktxprefs.annotation.DefaultInt",
    "co.windly.ktxprefs.annotation.DefaultLong",
    "co.windly.ktxprefs.annotation.DefaultString",
    "co.windly.ktxprefs.annotation.DefaultStringSet",
    "co.windly.ktxprefs.annotation.Mode",
    "co.windly.ktxprefs.annotation.Name",
    "co.windly.ktxprefs.annotation.Prefs"
  ]
)
@SupportedOptions(value = ["kapt.kotlin.generated"])
@SupportedSourceVersion(value = RELEASE_8)
class PrefsProcessor : AbstractProcessor() {

  //region Annotation Processor Configuration

  private object SuffixConfiguration {

    const val PREFS_WRAPPER = "Prefs"

    const val EDITOR_WRAPPER = "EditorWrapper"

    const val CONSTANTS = "Constants"
  }

  //endregion

  //region Process

  override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {

    // Print a message that annotation processor started.
    processingEnv.messager.printMessage(
      NOTE,
      "Ktx Prefs Annotation Processor had started."
    )

    annotations.forEach { annotation ->

      // Do nothing if incorrect annotated class has been passed to processor.
      if (annotation.qualifiedName.contentEquals("co.windly.ktxprefs.annotations.Prefs").not()) {
        return@forEach
      }

      // Iterate over all class elements.
      environment.getElementsAnnotatedWith(annotation).forEach { element ->

        // Retrieve class meta information (such as type, package, comment).
        val classElement = element as TypeElement
        val packageElement = classElement.enclosingElement as PackageElement
        val classComment = processingEnv.elementUtils.getDocComment(classElement)

        // Parse preferences.
        val prefList = mutableListOf<Pref>()

        // Iterate over the fields of given class.
        ElementFilter.fieldsIn(classElement.enclosedElements).forEach variable@{ variableElement ->

          // Ignore constants.
          if (variableElement.modifiers.contains(Modifier.STATIC)) {
            return@variable
          }

          // Retrieve field type information.
          val fieldType = variableElement.asType()

          // Check whether given type can be handled by this library.
          val isSupportedType = PrefType.isAllowedType(fieldType)

          // Close annotation processor with an error in case if there is unsupported field type.
          if (isSupportedType.not()) {

            // Print an error message.
            processingEnv.messager.printMessage(
              ERROR,
              "Processed class contains file type ($fieldType) which is not supported."
            )

            // Halt the annotation processor.
            return false
          }

          // Retrieve field meta information.
          val fieldName = variableElement.simpleName.toString()
          val fieldNameAnnotation = variableElement.getAnnotation(Name::class.java)
          val fieldComment = processingEnv.elementUtils.getDocComment(variableElement)

          // Prepare preference meta information.
          val preferenceName = getPreferenceName(fieldName, fieldNameAnnotation)
          val preferenceDefault = getPreferenceDefault(variableElement, fieldType)

          // Create a field preference descriptor.
          val preference = Pref(
            fieldName = fieldName,
            prefName = preferenceName,
            type = PrefType.from(fieldType),
            defaultValue = preferenceDefault,
            comment = fieldComment
          )

          // Add descriptor to preferences list.
          prefList += preference
        }

        // Prepare arguments container.
        val arguments = {
          "package" to packageElement.qualifiedName
          "comment" to classComment
          "constantsClassName" to "${classElement.simpleName}${SuffixConfiguration.CONSTANTS}"
          "prefList" to prefList
        }

        // Create shared preferences wrapper.
        processingEnv.filer
          .createSourceFile("${classElement.qualifiedName}${SuffixConfiguration.PREFS_WRAPPER}")
          .openWriter()
          .use { writer ->
            val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.PREFS_WRAPPER)
            template.process(arguments, writer)
          }

        // Create editor wrapper.
        processingEnv.filer
          .createSourceFile("${classElement.qualifiedName}${SuffixConfiguration.EDITOR_WRAPPER}")
          .openWriter()
          .use { writer ->
            val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.EDITOR_WRAPPER)
            template.process(arguments, writer)
          }

        // Create constants file that uses the following arguments.
        processingEnv.filer
          .createSourceFile("${classElement.qualifiedName}${SuffixConfiguration.CONSTANTS}")
          .openWriter()
          .use { writer ->
            val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.CONSTANTS)
            template.process(arguments, writer)
          }
      }
    }

    // Print a message that annotation processor stopped.
    processingEnv.messager.printMessage(
      NOTE,
      "Ktx Prefs Annotation Processor had finished it's work."
    )

    return true
  }

  private fun getPreferenceName(fieldName: String, fieldNameAnnotation: Name?): String? =
    fieldNameAnnotation?.value ?: fieldName

  private fun getPreferenceDefault(variableElement: VariableElement, fieldType: TypeMirror): String? {

    // Parse for Boolean.
    variableElement.getAnnotation(DefaultBoolean::class.java)?.let {
      return when (isAnnotationSupported(BOOLEAN, fieldType)) {
        false -> null
        true -> it.value.toString()
      }
    }

    // Parse for Float.
    variableElement.getAnnotation(DefaultFloat::class.java)?.let {
      return when (isAnnotationSupported(FLOAT, fieldType)) {
        false -> null
        true -> it.value.toString() + "f"
      }
    }

    // Parse for Int.
    variableElement.getAnnotation(DefaultInt::class.java)?.let {
      return when (isAnnotationSupported(INTEGER, fieldType)) {
        false -> null
        true -> it.value.toString()
      }
    }

    // Parse for Long.
    variableElement.getAnnotation(DefaultLong::class.java)?.let {
      return when (isAnnotationSupported(LONG, fieldType)) {
        false -> null
        true -> it.value.toString() + "L"
      }
    }

    // Parse for String.
    variableElement.getAnnotation(DefaultString::class.java)?.let {
      return when (isAnnotationSupported(STRING, fieldType)) {
        false -> null
        true -> wrap(escapeJava(it.value), "\"")
      }
    }

    variableElement.getAnnotation(DefaultStringSet::class.java)?.let {
      // TODO:
    }

    // Return null value for unsupported types.
    return "null"
  }

  private fun isAnnotationSupported(preferenceType: PrefType, fieldType: TypeMirror): Boolean {

    if (preferenceType.isCompatible(fieldType).not()) {

      // Print an error message.
      processingEnv.messager.printMessage(
        ERROR,
        "Processed class contains file type ($fieldType) which is not supported."
      )

      // Halt the annotation processor.
      return false
    }
    return true
  }

  //endregion

  //region Template Processor Configuration

  private object FreemarkerConfiguration {

    const val MAJOR_VERSION = 2

    const val MINOR_VERSION = 3

    const val MICRO_VERSION = 28

    const val BASE_PACKAGE_PATH = ""
  }

  private object FreemarkerTemplate {

    const val PREFS_WRAPPER = "shared_preferences_wrapper.ftl"

    const val EDITOR_WRAPPER = "editor_wrapper.ftl"

    const val CONSTANTS = "constants.ftl"
  }

  private val freemarkerVersion: Version
    by lazy { Version(MAJOR_VERSION, MINOR_VERSION, MICRO_VERSION) }

  private val freemarkerConfiguration: Configuration
    by lazy {
      Configuration(freemarkerVersion)
        .also { it.setClassForTemplateLoading(javaClass, BASE_PACKAGE_PATH) }
    }

  //endregion
}
