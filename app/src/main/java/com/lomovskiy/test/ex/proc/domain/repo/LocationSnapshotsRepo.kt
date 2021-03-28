package com.lomovskiy.test.ex.proc.domain.repo

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import com.lomovskiy.test.ex.proc.domain.LocationException
import kotlin.jvm.Throws

interface LocationSnapshotsRepo {

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    @Throws(LocationException::class)
    suspend fun getCurrent(): Location?

}
