package co.windly.ktxprefs.sample.kotlin.application

import co.windly.ktxprefs.sample.kotlin.persistence.PersistenceModule
import co.windly.ktxprefs.sample.kotlin.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ApplicationModule::class,
    PersistenceModule::class,
    PresentationModule::class
  ]
)
interface ApplicationComponent: AndroidInjector<SampleApplication> {

  //region Component Builder

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: SampleApplication): Builder

    fun build(): ApplicationComponent
  }

  //endregion
}
