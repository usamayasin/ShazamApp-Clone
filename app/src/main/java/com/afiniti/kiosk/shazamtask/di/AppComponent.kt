package com.afiniti.kiosk.shazamtask.di

import com.afiniti.kiosk.shazamtask.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        HomeFragmentModule::class,
        ViewModelModule::class,
        ApiModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(application: MyApplication)
}