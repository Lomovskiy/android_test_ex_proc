package com.lomovskiy.test.ex.proc.data.repo

import com.lomovskiy.test.ex.proc.data.IMAGE_PATH_TEMPLATE
import com.lomovskiy.test.ex.proc.data.RemoteApi
import com.lomovskiy.test.ex.proc.data.local.WeatherSnapshotDbEntity
import com.lomovskiy.test.ex.proc.data.local.WeatherSnapshotsDao
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.threeten.bp.Instant
import javax.inject.Inject

class WeatherSnapshotRepoImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val remoteApi: RemoteApi,
    private val weatherSnapshotsDao: WeatherSnapshotsDao
) : WeatherSnapshotRepo {

    override suspend fun create(entity: WeatherSnapshotEntity) {
        withContext(dispatcher) {
            weatherSnapshotsDao.deleteAll()
            weatherSnapshotsDao.insert(
                mapToDbEntity(entity)
            )
        }
    }

    override suspend fun read(): WeatherSnapshotEntity? {
        return withContext(dispatcher) {
            return@withContext weatherSnapshotsDao.getAll().firstOrNull()?.let(::mapToEntity)
        }
    }

    override suspend fun fetch(): WeatherSnapshotEntity {
        return withContext(dispatcher) {
            val response = remoteApi.getWeatherSnapshotByCoordinates(
                0.0,
                0.0
            ).execute()
            val root = JSONObject(String(response.body()!!.bytes()))
            val current: JSONObject = root.getJSONObject("current")
            return@withContext WeatherSnapshotEntity(
                current.getDouble("temp"),
                current.getDouble("wind_speed"),
                IMAGE_PATH_TEMPLATE.format(
                    current.getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("icon")
                ),
                Instant.now().epochSecond
            )
        }
    }

    private fun mapToDbEntity(entity: WeatherSnapshotEntity): WeatherSnapshotDbEntity {
        return WeatherSnapshotDbEntity(
            temp = entity.temp,
            speed = entity.speed,
            imagePath = entity.imagePath,
            createdTimestamp = entity.createdTimestamp
        )
    }

    private fun mapToEntity(dbEntity: WeatherSnapshotDbEntity): WeatherSnapshotEntity {
        return WeatherSnapshotEntity(
            temp = dbEntity.temp,
            speed = dbEntity.speed,
            imagePath = dbEntity.imagePath,
            createdTimestamp = dbEntity.createdTimestamp
        )
    }

}
