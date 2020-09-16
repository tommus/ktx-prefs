package co.windly.ktxprefs.sample.kotlin.presentation

import co.windly.ktxprefs.sample.kotlin.presentation.coroutine.CoroutineModule
import co.windly.ktxprefs.sample.kotlin.presentation.reactive.ReactiveModule
import co.windly.ktxprefs.sample.kotlin.presentation.simple.SimpleModule
import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [
  AndroidInjectionModule::class,
  CoroutineModule::class,
  ReactiveModule::class,
  SimpleModule::class
])
class PresentationModule
