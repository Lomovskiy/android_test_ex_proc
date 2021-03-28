package com.lomovskiy.test.ex.proc.domain

import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import org.threeten.bp.Instant
import javax.inject.Inject

interface WeatherInteractor {

    suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity

}

class WeatherInteractorImpl @Inject constructor(
    private val weatherSnapshotsRepo: WeatherSnapshotsRepo
) : WeatherInteractor {

    override suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity {
        val latestWeatherSnapshot: WeatherSnapshotEntity? = weatherSnapshotsRepo.readAll().firstOrNull()
        val currentTimestamp: Long = Instant.now().epochSecond
        if (latestWeatherSnapshot == null || currentTimestamp - latestWeatherSnapshot.createdTimestamp > (1 * 60)) {

        }
        return weatherSnapshotsRepo.readAll().first()
    }

}
