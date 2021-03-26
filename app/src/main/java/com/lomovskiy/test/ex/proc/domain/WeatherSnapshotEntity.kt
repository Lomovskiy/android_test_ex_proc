package com.lomovskiy.test.ex.proc.domain

class WeatherSnapshotEntity(
    val temperature: Int,
    val speed: Int,
    val imagePath: String?
) {

    companion object {

        fun stub(): WeatherSnapshotEntity {
            return WeatherSnapshotEntity(
                temperature = 20,
                speed = 3,
                imagePath = null
            )
        }

    }

}
