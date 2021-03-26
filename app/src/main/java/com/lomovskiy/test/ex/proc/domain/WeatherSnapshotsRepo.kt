package com.lomovskiy.test.ex.proc.domain

interface WeatherSnapshotsRepo {

    suspend fun create(entity: WeatherSnapshotEntity)

    fun readAll(): List<WeatherSnapshotEntity>

}
