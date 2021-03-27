package com.lomovskiy.test.ex.proc.data.repo

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

    override suspend fun fetch() {
        withContext(dispatcher) {
            val response = remoteApi.getWeatherSnapshotByCoordinates(
                0.0,
                0.0
            ).execute()
            if (!response.isSuccessful) {
                return@withContext
            }
            val json = JSONObject(String(response.body()!!.bytes()))
            val weatherSnapshotEntity: WeatherSnapshotEntity = WeatherSnapshotEntity(
                json.getJSONObject("current").getDouble("temp"),
                json.getJSONObject("current").getDouble("wind_speed"),
                "1",
                Instant.now().epochSecond
            )
            create(weatherSnapshotEntity)
        }
    }

}
