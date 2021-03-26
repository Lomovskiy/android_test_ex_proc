package com.lomovskiy.test.ex.proc.domain

import kotlinx.coroutines.delay

interface WeatherInteractor {

    suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity

}

class WeatherInteractorImpl(
    private val weatherSnapshotsRepo: WeatherSnapshotsRepo
) : WeatherInteractor {

    override suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity {
        delay(3000)
        return WeatherSnapshotEntity.stub()
    }

}
