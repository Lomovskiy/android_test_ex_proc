package com.lomovskiy.test.ex.proc.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.SettingsClient
import com.lomovskiy.test.ex.proc.data.ENDPOINT
import com.lomovskiy.test.ex.proc.data.RemoteApi
import com.lomovskiy.test.ex.proc.data.SigningInterceptor
import com.lomovskiy.test.ex.proc.data.repo.GooglePlayServicesStatusRepoImpl
import com.lomovskiy.test.ex.proc.data.repo.LocationSnapshotsRepoImpl
import com.lomovskiy.test.ex.proc.data.repo.OpenWeatherMapAppKeysRepoImpl
import com.lomovskiy.test.ex.proc.data.repo.WeatherSnapshotsRepoImpl
import com.lomovskiy.test.ex.proc.domain.WeatherInteractor
import com.lomovskiy.test.ex.proc.domain.WeatherInteractorImpl
import com.lomovskiy.test.ex.proc.domain.repo.GooglePlayServicesStatusRepo
import com.lomovskiy.test.ex.proc.domain.repo.LocationSnapshotsRepo
import com.lomovskiy.test.ex.proc.domain.repo.OpenWeatherMapAppKeysRepo
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class AppModule {

    companion object {

        @Provides
        @Singleton
        fun provideLocationProvider(context: Context): FusedLocationProviderClient {
            return FusedLocationProviderClient(context)
        }

        @Provides
        @Singleton
        fun provideLocationSettingsProvider(context: Context): SettingsClient {
            return SettingsClient(context)
        }

        @Provides
        fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher {
            return Dispatchers.Default
        }

        @Provides
        @Singleton
        fun provideRemoteApi(httpClient: OkHttpClient): RemoteApi {
            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(ENDPOINT)
                .build()
                .create(RemoteApi::class.java)
        }

        @Provides
        @Singleton
        fun provideHttpClient(signingInterceptor: SigningInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(signingInterceptor)
                .build()
        }

    }

    @Binds
    @Singleton
    abstract fun bindWeatherInteractor(impl: WeatherInteractorImpl): WeatherInteractor

    @Binds
    @Singleton
    abstract fun bindLocationSnapshotsRepo(impl: LocationSnapshotsRepoImpl): LocationSnapshotsRepo

    @Binds
    @Singleton
    abstract fun bindGooglePlayServicesStatusRepo(impl: GooglePlayServicesStatusRepoImpl): GooglePlayServicesStatusRepo

    @Binds
    @Singleton
    abstract fun bindOpenWeatherMapAppKeysRepo(impl: OpenWeatherMapAppKeysRepoImpl): OpenWeatherMapAppKeysRepo

    @Binds
    @Singleton
    abstract fun weatherSnapshotsRepo(impl: WeatherSnapshotsRepoImpl): WeatherSnapshotsRepo

}
