package co.windly.ktxprefs.sample.kotlin.persistence

import co.windly.ktxprefs.sample.kotlin.persistence.shared.SharedPreferenceModule
import dagger.Module

@Module(includes = [SharedPreferenceModule::class])
class PersistenceModule
