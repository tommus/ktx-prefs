package co.windly.ktxprefs.sample.kotlinsimple.persistence

import co.windly.ktxprefs.sample.kotlinsimple.persistence.shared.SharedPreferenceModule
import dagger.Module

@Module(includes = [SharedPreferenceModule::class])
class PersistenceModule
