package com.lomovskiy.test.ex.proc.domain.repo

import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity

interface WeatherSnapshotRepo {

    suspend fun create(entity: WeatherSnapshotEntity)

    suspend fun read(): WeatherSnapshotEntity?

    suspend fun fetch(): WeatherSnapshotEntity

}
