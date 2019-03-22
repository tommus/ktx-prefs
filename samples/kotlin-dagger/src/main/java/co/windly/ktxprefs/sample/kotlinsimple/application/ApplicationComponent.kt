package co.windly.ktxprefs.sample.kotlinsimple.application

import co.windly.ktxprefs.sample.kotlinsimple.persistence.PersistenceModule
import co.windly.ktxprefs.sample.kotlinsimple.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ApplicationModule::class,
    PersistenceModule::class,
    PresentationModule::class
  ]
)
interface ApplicationComponent {

  //region Injection

  fun inject(applicationModule: KotlinDagger)

  //endregion

  //region Component Builder

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: KotlinDagger): Builder

    fun build(): ApplicationComponent
  }

  //endregion

  //region Component Provider

  interface ComponentProvider {

    val applicationComponent: ApplicationComponent
  }

  //endregion
}
