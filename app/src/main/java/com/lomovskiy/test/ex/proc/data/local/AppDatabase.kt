package com.lomovskiy.test.ex.proc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeatherSnapshotDbEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherSnapshotsDao(): WeatherSnapshotsDao

}
