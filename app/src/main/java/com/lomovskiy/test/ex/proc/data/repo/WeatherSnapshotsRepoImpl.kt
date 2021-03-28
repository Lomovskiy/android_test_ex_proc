package com.lomovskiy.test.ex.proc.data.repo

import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherSnapshotsRepoImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : WeatherSnapshotsRepo {

    private var cache: List<WeatherSnapshotEntity> = mutableListOf()

    override suspend fun create(entity: WeatherSnapshotEntity) {
        withContext(dispatcher) {
            cache = listOf(entity)
        }
    }

    override fun readAll(): List<WeatherSnapshotEntity> {
        return cache
    }

}
