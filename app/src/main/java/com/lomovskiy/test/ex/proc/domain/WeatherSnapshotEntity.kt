package com.lomovskiy.test.ex.proc.domain

import org.threeten.bp.Instant

class WeatherSnapshotEntity(
    val temperature: Double,
    val speed: Double,
    val imagePath: String?,
    val createdTimestamp: Long
) {

    companion object {

        fun stub(): WeatherSnapshotEntity {
            return WeatherSnapshotEntity(
                temperature = 20.0,
                speed = 3.0,
                imagePath = null,
                createdTimestamp = Instant.now().epochSecond
            )
        }

    }

}
