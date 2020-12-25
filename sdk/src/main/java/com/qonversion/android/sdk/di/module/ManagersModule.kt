package com.qonversion.android.sdk.di.module

import android.content.SharedPreferences
import com.qonversion.android.sdk.QonversionRepository
import com.qonversion.android.sdk.di.scope.ApplicationScope
import com.qonversion.android.sdk.push.QAutomationManager
import dagger.Module
import dagger.Provides

@Module
class ManagersModule {

    @ApplicationScope
    @Provides
    fun provideAutomationManager(
        repository: QonversionRepository,
        preferences: SharedPreferences
    ): QAutomationManager {
        return QAutomationManager(repository, preferences)
    }
}
