package com.lomovskiy.test.ex.proc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherSnapshotsDao {

    @Insert
    abstract fun insert(dbEntity: WeatherSnapshotDbEntity)

    @Query("""
        SELECT *
        FROM `${WeatherSnapshotDbEntity.TABLE_NAME}`
    """)
    abstract fun getAll(): Flow<List<WeatherSnapshotDbEntity>>

}
