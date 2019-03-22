package co.windly.ktxprefs.sample.kotlinsimple.presentation

import co.windly.ktxprefs.sample.kotlinsimple.presentation.main.MainModule
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [
  AndroidSupportInjectionModule::class,
  MainModule::class
])
class PresentationModule
