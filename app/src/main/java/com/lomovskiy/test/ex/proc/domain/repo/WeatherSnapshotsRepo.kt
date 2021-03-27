package com.lomovskiy.test.ex.proc.domain.repo

import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity

interface WeatherSnapshotsRepo {

    suspend fun create(entity: WeatherSnapshotEntity)

    fun readAll(): List<WeatherSnapshotEntity>

}
