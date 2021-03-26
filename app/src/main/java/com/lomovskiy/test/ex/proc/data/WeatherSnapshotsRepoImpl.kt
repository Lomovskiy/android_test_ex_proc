package com.lomovskiy.test.ex.proc.data

import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherSnapshotsRepoImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : WeatherSnapshotsRepo {

    private val cache = mutableListOf<WeatherSnapshotEntity>()

    override suspend fun create(entity: WeatherSnapshotEntity) {
        withContext(dispatcher) {
        }
    }

    override fun readAll(): List<WeatherSnapshotEntity> {
        return cache
    }

}
