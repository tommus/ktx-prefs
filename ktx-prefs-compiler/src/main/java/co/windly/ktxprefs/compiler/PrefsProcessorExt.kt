package co.windly.ktxprefs.compiler

import javax.annotation.processing.Messager
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.NOTE

//region Note

internal fun Messager.noteMessage(message: () -> String) {
  printMessage(NOTE, message())
}

//endregion

//region Error

internal fun Messager.errorMessage(message: () -> String) {
  printMessage(ERROR, message())
}

//endregion
