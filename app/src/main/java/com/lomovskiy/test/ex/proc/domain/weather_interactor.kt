package com.lomovskiy.test.ex.proc.domain

import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotRepo
import org.threeten.bp.Instant
import javax.inject.Inject

interface WeatherInteractor {

    suspend fun getWeatherSnapshot(): WeatherSnapshotEntity

}

class WeatherInteractorImpl @Inject constructor(
    private val weatherSnapshotRepo: WeatherSnapshotRepo
) : WeatherInteractor {

    override suspend fun getWeatherSnapshot(): WeatherSnapshotEntity {
        val latestWeatherSnapshot: WeatherSnapshotEntity? = weatherSnapshotRepo.read()
        val currentTimestamp: Long = Instant.now().epochSecond
        if (latestWeatherSnapshot == null || currentTimestamp - latestWeatherSnapshot.createdTimestamp > (1 * 60)) {
            val newWeatherSnapshot: WeatherSnapshotEntity = weatherSnapshotRepo.fetch()
            weatherSnapshotRepo.create(newWeatherSnapshot)
        }
        return weatherSnapshotRepo.read()!!
    }

}
