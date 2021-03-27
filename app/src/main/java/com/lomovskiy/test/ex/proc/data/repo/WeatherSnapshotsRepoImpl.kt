package com.lomovskiy.test.ex.proc.data.repo

import com.lomovskiy.test.ex.proc.data.IMAGE_PATH_TEMPLATE
import com.lomovskiy.test.ex.proc.data.remoteApi
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.threeten.bp.Instant
import retrofit2.Response

class WeatherSnapshotsRepoImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
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
