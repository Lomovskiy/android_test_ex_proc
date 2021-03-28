package com.lomovskiy.test.ex.proc.domain

import android.location.Location
import com.lomovskiy.test.ex.proc.domain.repo.LocationSnapshotsRepo
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import org.threeten.bp.Instant
import javax.inject.Inject

interface WeatherInteractor {

    suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity

}

class WeatherInteractorImpl @Inject constructor(
    private val weatherSnapshotsRepo: WeatherSnapshotsRepo,
    private val locationSnapshotsRepo: LocationSnapshotsRepo
) : WeatherInteractor {

    override suspend fun getLatestWeatherSnapshot(): WeatherSnapshotEntity {
        val locationSnapshot: Location? = locationSnapshotsRepo.getCurrent()
        val latestWeatherSnapshot: WeatherSnapshotEntity? = weatherSnapshotsRepo.readAll().firstOrNull()
        val currentTimestamp: Long = Instant.now().epochSecond
        if (latestWeatherSnapshot == null || currentTimestamp - latestWeatherSnapshot.createdTimestamp > (1 * 60)) {

        }
        return weatherSnapshotsRepo.readAll().first()
    }

}
