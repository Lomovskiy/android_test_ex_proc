package com.lomovskiy.test.ex.proc.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commit
import com.lomovskiy.test.ex.proc.data.WeatherSnapshotsRepoImpl
import com.lomovskiy.test.ex.proc.domain.WeatherInteractor
import com.lomovskiy.test.ex.proc.domain.WeatherInteractorImpl

class ScreensFactory(
    private val interactor: WeatherInteractor
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return ScreenWeather(interactor)
    }

}

class MainActivity : AppCompatActivity() {

    private val interactor: WeatherInteractor = WeatherInteractorImpl(WeatherSnapshotsRepoImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = ScreensFactory(interactor)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(android.R.id.content, ScreenWeather::class.java, null, ScreenWeather::class.java.name)
            }
        }
    }

}
