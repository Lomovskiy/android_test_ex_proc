package com.lomovskiy.test.ex.proc.di

import android.content.Context
import com.lomovskiy.test.ex.proc.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class
])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent

    }

    fun inject(target: MainActivity)

}
