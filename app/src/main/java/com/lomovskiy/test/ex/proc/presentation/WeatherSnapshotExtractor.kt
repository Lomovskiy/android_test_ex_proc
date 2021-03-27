package com.lomovskiy.test.ex.proc.presentation

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lomovskiy.test.ex.proc.data.IMAGE_PATH_TEMPLATE
import com.lomovskiy.test.ex.proc.data.RemoteApi
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.lomovskiy.test.ex.proc.domain.repo.WeatherSnapshotsRepo
import org.json.JSONObject
import org.threeten.bp.Instant

class WeatherSnapshotExtractor(
    context: Context,
    workerParameters: WorkerParameters,
    private val remoteApi: RemoteApi,
    private val weatherSnapshotsRepo: WeatherSnapshotsRepo
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val weatherSnapshotEntity: WeatherSnapshotEntity = extract()
        weatherSnapshotsRepo.create(weatherSnapshotEntity)
        return Result.success()
    }

    private fun extract(): WeatherSnapshotEntity {
        val response = remoteApi.getWeatherSnapshotByCoordinates(
            0.0,
            0.0
        ).execute()
        val root = JSONObject(String(response.body()!!.bytes()))
        val current: JSONObject = root.getJSONObject("current")
        return WeatherSnapshotEntity(
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