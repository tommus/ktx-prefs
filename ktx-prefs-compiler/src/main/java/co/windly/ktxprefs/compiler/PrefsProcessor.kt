package co.windly.ktxprefs.compiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes(
  value = [
    "co.windly.ktxprefs.annotations.DefaultBoolean",
    "co.windly.ktxprefs.annotations.DefaultFloat",
    "co.windly.ktxprefs.annotations.DefaultInt",
    "co.windly.ktxprefs.annotations.DefaultLong",
    "co.windly.ktxprefs.annotations.DefaultString",
    "co.windly.ktxprefs.annotations.DefaultStringSet",
    "co.windly.ktxprefs.annotations.Mode",
    "co.windly.ktxprefs.annotations.Name",
    "co.windly.ktxprefs.annotations.Prefs"
  ]
)
class PrefsProcessor : AbstractProcessor() {

  //region Process

  override fun process(annotations: MutableSet<out TypeElement>?, environment: RoundEnvironment?): Boolean {
    TODO("not implemented")
  }

  //endregion
}
