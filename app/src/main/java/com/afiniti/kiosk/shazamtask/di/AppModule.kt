package com.afiniti.kiosk.shazamtask.di

import android.content.Context
import com.afiniti.kiosk.shazamtask.utils.stringUtil.StringProvider
import com.afiniti.kiosk.shazamtask.utils.stringUtil.StringProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideStringProviderInterface(context:Context): StringProvider {
        return StringProviderImpl(context)
    }
}