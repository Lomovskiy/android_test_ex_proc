package com.lomovskiy.test.ex.proc.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeatherSnapshotDbEntity.TABLE_NAME)
class WeatherSnapshotDbEntity(

    @ColumnInfo(name = COLUMN_NAME_TEMP)
    val temp: Double,

    @ColumnInfo(name = COLUMN_NAME_SPEED)
    val speed: Double,

    @ColumnInfo(name = COLUMN_NAME_IMAGE_PATH)
    val imagePath: String,

    @ColumnInfo(name = COLUMN_NAME_CREATED_TIMESTAMP)
    val createdTimestamp: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_LOCAL_ID)
    var localId: Long? = null

) {

    companion object {

        const val TABLE_NAME = "weather_snapshot"
        const val COLUMN_NAME_LOCAL_ID = "local_id"
        const val COLUMN_NAME_TEMP = "temp"
        const val COLUMN_NAME_SPEED = "speed"
        const val COLUMN_NAME_IMAGE_PATH = "image_path"
        const val COLUMN_NAME_CREATED_TIMESTAMP = "created_timestamp"

    }

}
