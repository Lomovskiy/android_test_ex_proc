package com.lomovskiy.test.ex.proc.domain

import androidx.work.WorkManager
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import org.threeten.bp.Instant

interface WeatherInteractor {

    suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity

}

class WeatherInteractorImpl(
    private val weatherSnapshotsRepo: WeatherSnapshotsRepo,
    private val workManager: WorkManager
) : WeatherInteractor {

    override suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity {
        val latestWeatherSnapshot: WeatherSnapshotEntity? = weatherSnapshotsRepo.readAll().firstOrNull()
        val currentTimestamp: Long = Instant.now().epochSecond
        if (latestWeatherSnapshot == null || currentTimestamp - latestWeatherSnapshot.createdTimestamp > (1 * 60)) {

        }
        return weatherSnapshotsRepo.readAll().first()
    }

}
