package com.lomovskiy.test.ex.proc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherSnapshotsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(dbEntity: WeatherSnapshotDbEntity)

    @Query("""
        SELECT *
        FROM `${WeatherSnapshotDbEntity.TABLE_NAME}`
    """)
    abstract fun getAll(): List<WeatherSnapshotDbEntity>

    @Query("""
        DELETE
        FROM `${WeatherSnapshotDbEntity.TABLE_NAME}`
    """)
    abstract fun deleteAll()

}
