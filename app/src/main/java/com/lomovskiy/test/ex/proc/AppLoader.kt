package com.lomovskiy.test.ex.proc

import android.app.Application
import com.lomovskiy.test.ex.proc.di.AppComponent
import com.lomovskiy.test.ex.proc.di.DaggerAppComponent

class AppLoader : Application() {

    companion object {

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(this)
    }

}