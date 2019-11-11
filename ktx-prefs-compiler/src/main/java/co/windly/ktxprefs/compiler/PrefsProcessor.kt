package co.windly.ktxprefs.compiler

import co.windly.ktxprefs.annotation.DefaultBoolean
import co.windly.ktxprefs.annotation.DefaultFloat
import co.windly.ktxprefs.annotation.DefaultInt
import co.windly.ktxprefs.annotation.DefaultLong
import co.windly.ktxprefs.annotation.DefaultString
import co.windly.ktxprefs.annotation.Name
import co.windly.ktxprefs.annotation.Prefs
import co.windly.ktxprefs.annotation.Reactive
import co.windly.ktxprefs.compiler.PrefType.BOOLEAN
import co.windly.ktxprefs.compiler.PrefType.FLOAT
import co.windly.ktxprefs.compiler.PrefType.INTEGER
import co.windly.ktxprefs.compiler.PrefType.LONG
import co.windly.ktxprefs.compiler.PrefType.STRING
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.BASE_PACKAGE_PATH
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MAJOR_VERSION
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MICRO_VERSION
import co.windly.ktxprefs.compiler.PrefsProcessor.FreemarkerConfiguration.MINOR_VERSION
import co.windly.ktxprefs.compiler.PrefsProcessor.ProcessorConfiguration
import freemarker.template.Configuration
import freemarker.template.Version
import org.apache.commons.text.StringEscapeUtils
import java.io.File
import java.nio.charset.Charset
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

@SupportedAnnotationTypes(
  value = [
    "co.windly.ktxprefs.annotation.DefaultBoolean",
    "co.windly.ktxprefs.annotation.DefaultFloat",
    "co.windly.ktxprefs.annotation.DefaultInt",
    "co.windly.ktxprefs.annotation.DefaultLong",
    "co.windly.ktxprefs.annotation.DefaultString",
    "co.windly.ktxprefs.annotation.Name",
    "co.windly.ktxprefs.annotation.Prefs",
    "co.windly.ktxprefs.annotation.Reactive"
  ]
)
@SupportedOptions(value = [ProcessorConfiguration.OPTION_KAPT_KOTLIN])
@SupportedSourceVersion(value = RELEASE_8)
class PrefsProcessor : AbstractProcessor() {

  //region Annotation Processor Configuration

  internal object ProcessorConfiguration {

    const val OPTION_KAPT_KOTLIN = "kapt.kotlin.generated"

    const val SRC_KAPT_KOTLIN = "kaptKotlin"

    const val SRC_KAPT = "kapt"
  }

  private object SuffixConfiguration {

    const val PREFS_WRAPPER = "Prefs"

    const val EDITOR_WRAPPER = "EditorWrapper"

    const val CONSTANTS = "Constants"

    const val EXTENSION = "Ext"
  }

  //endregion

  //region Process

  override fun process(annotations: MutableSet<out TypeElement>?,
    environment: RoundEnvironment): Boolean {

    // Print a message that annotation processor started.
    processingEnv.messager.noteMessage { "Ktx Prefs Annotation Processor had started." }

    // Stop processing in case if there are no annotations declared.
    if (annotations == null) {
      return true
    }

    // Check whether Kotlin files target directory is accessible.
    val kaptTargetDirectory = processingEnv.options[ProcessorConfiguration.OPTION_KAPT_KOTLIN]
      ?: run {

        // Log an error message.
        processingEnv.messager.errorMessage { "Cannot access Kotlin files target directory." }

        // Stop processing in case if an error occurred.
        return true
      }

    annotations.forEach { annotation ->

      // Do nothing if incorrect annotated class has been passed to processor.
      if (annotation.qualifiedName.contentEquals("co.windly.ktxprefs.annotation.Prefs").not()) {
        return@forEach
      }

      // Iterate over all class elements.
      environment.getElementsAnnotatedWith(annotation).forEach { element ->

        // Retrieve a class meta information.
        val classElement = element as TypeElement
        val packageElement = classElement.enclosingElement as PackageElement
        val classComment = processingEnv.elementUtils.getDocComment(classElement)

        // Retrieve a class reactive meta information.
        val classReactive = classElement.getAnnotation(Reactive::class.java)
        val classEnableReactive = classReactive?.value ?: true
        val classDistinctUntilChanged = classReactive?.distinctUntilChanged ?: true

        // Prepare preferences collection.
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
            processingEnv.messager
              .errorMessage { "Processed class contains field type ($fieldType) which is not supported." }

            // Halt the annotation processor.
            return true
          }

          // Retrieve field meta information.
          val fieldName = variableElement.simpleName.toString()
          val fieldNameAnnotation = variableElement.getAnnotation(Name::class.java)
          val fieldComment = processingEnv.elementUtils.getDocComment(variableElement)

          // Prepare preference meta information.
          val preferenceName = getPreferenceName(fieldName, fieldNameAnnotation)
          val preferenceDefault = getPreferenceDefault(variableElement, fieldType)

          // Retrieve reactive meta information.
          val variableReactive = variableElement.getAnnotation(Reactive::class.java)
          val enableReactive = variableReactive?.value ?: classEnableReactive ?: true
          val distinctUntilChanged = variableReactive?.distinctUntilChanged
            ?: classDistinctUntilChanged ?: true

          // Create a field preference descriptor.
          val preference = Pref(
            fieldName = fieldName,
            prefName = preferenceName,
            type = PrefType.from(fieldType),
            defaultValue = preferenceDefault,
            comment = fieldComment,
            enableReactive = enableReactive,
            distinctUntilChanged = distinctUntilChanged
          )

          // Add descriptor to preferences list.
          prefList += preference
        }

        // Retrieve a class prefs annotation.
        val prefsAnnotation = classElement.getAnnotation(Prefs::class.java)

        // Retrieve file name.
        val filename = when (prefsAnnotation.value.isEmpty()) {
          false -> prefsAnnotation.value
          true -> prefsAnnotation.fileName
        }

        // Prepare arguments container.
        val arguments: MutableMap<String, Any?> = mutableMapOf()

        // Prepare class basics arguments.
        arguments["fileName"] = filename
        arguments["package"] = packageElement.qualifiedName
        arguments["comment"] = classComment

        // Prepare generated names arguments.
        arguments["prefWrapperClassName"] = "${classElement.simpleName}${SuffixConfiguration.PREFS_WRAPPER}"
        arguments["editorWrapperClassName"] = "${classElement.simpleName}${SuffixConfiguration.EDITOR_WRAPPER}"
        arguments["constantsClassName"] = "${classElement.simpleName}${SuffixConfiguration.CONSTANTS}"
        arguments["extensionClassName"] = "${classElement.simpleName}"

        // Prepare preferences list.
        arguments["prefList"] = prefList

        // Make directory for generated files.
        val packageDirectory = File(
          kaptTargetDirectory.replace(ProcessorConfiguration.SRC_KAPT_KOTLIN,
            ProcessorConfiguration.SRC_KAPT),
          getPackagePath(packageElement))
          .also { it.mkdirs() }

        // Create shared preferences wrapper extensions.
        File(packageDirectory,
          "${classElement.simpleName}${SuffixConfiguration.EXTENSION}.kt").apply {
          writer(Charset.defaultCharset())
            .use { writer ->
              val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.EXTENSIONS)
              template.process(arguments, writer)
            }
        }

        // Create shared preferences wrapper.
        File(packageDirectory,
          "${classElement.simpleName}${SuffixConfiguration.PREFS_WRAPPER}.kt").apply {
          writer(Charset.defaultCharset())
            .use { writer ->
              val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.PREFS_WRAPPER)
              template.process(arguments, writer)
            }
        }

        // Create editor wrapper.
        File(packageDirectory,
          "${classElement.simpleName}${SuffixConfiguration.EDITOR_WRAPPER}.kt").apply {
          writer(Charset.defaultCharset())
            .use { writer ->
              val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.EDITOR_WRAPPER)
              template.process(arguments, writer)
            }
        }

        // Create constants file that uses the following arguments.
        File(packageDirectory,
          "${classElement.simpleName}${SuffixConfiguration.CONSTANTS}.kt").apply {
          writer(Charset.defaultCharset())
            .use { writer ->
              val template = freemarkerConfiguration.getTemplate(FreemarkerTemplate.CONSTANTS)
              template.process(arguments, writer)
            }
        }
      }
    }

    // Print a message that annotation processor stopped.
    processingEnv.messager.noteMessage { "Ktx Prefs Annotation Processor had finished it's work." }

    return true
  }

  private fun getPackagePath(packageElement: PackageElement): String =
    packageElement.qualifiedName.toString().replace(".", "/")

  private fun getPreferenceName(fieldName: String, fieldNameAnnotation: Name?): String? =
    fieldNameAnnotation?.value ?: fieldName

  private fun getPreferenceDefault(variableElement: VariableElement,
    fieldType: TypeMirror): String? {

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
        true -> "${it.value}f"
      }
    }

    // Parse for Int.
    variableElement.getAnnotation(DefaultInt::class.java)?.let {
      return when (isAnnotationSupported(INTEGER, fieldType)) {
        false -> null
        true -> "${it.value}"
      }
    }

    // Parse for Long.
    variableElement.getAnnotation(DefaultLong::class.java)?.let {
      return when (isAnnotationSupported(LONG, fieldType)) {
        false -> null
        true -> "${it.value}L"
      }
    }

    // Parse for String.
    variableElement.getAnnotation(DefaultString::class.java)?.let {
      return when (isAnnotationSupported(STRING, fieldType)) {
        false -> null
        true -> "\"${StringEscapeUtils.escapeJava(it.value)}\""
      }
    }

    // Return null value for unsupported types.
    return "null"
  }

  private fun isAnnotationSupported(preferenceType: PrefType, fieldType: TypeMirror): Boolean {

    if (preferenceType.isCompatible(fieldType).not()) {

      // Print an error message.
      processingEnv.messager.errorMessage { "Processed class contains field type ($fieldType) which is not supported." }

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

    const val EXTENSIONS = "extensions.ftl"

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
