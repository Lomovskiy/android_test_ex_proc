package com.lomovskiy.test.ex.proc.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commit
import com.lomovskiy.test.ex.proc.AppLoader
import com.lomovskiy.test.ex.proc.domain.WeatherInteractor
import javax.inject.Inject

class ScreensFactory(
    private val interactor: WeatherInteractor
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return ScreenWeather(interactor)
    }

}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var interactor: WeatherInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        AppLoader.appComponent.inject(this)
        supportFragmentManager.fragmentFactory = ScreensFactory(interactor)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(android.R.id.content, ScreenWeather::class.java, null, ScreenWeather::class.java.name)
            }
        }
    }

}
