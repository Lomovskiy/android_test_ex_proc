package com.lomovskiy.test.ex.proc.data.repo

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.lomovskiy.test.ex.proc.domain.LocationException
import com.lomovskiy.test.ex.proc.domain.repo.LocationSnapshotsRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.Throws

class LocationSnapshotsRepoImpl @Inject constructor(
    private val locationSettingsProvider: SettingsClient,
    private val locationProvider: FusedLocationProviderClient
) : LocationSnapshotsRepo {

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    @Throws(LocationException::class)
    override suspend fun getCurrent(): Location? {
        try {
            locationSettingsProvider.checkLocationSettings(
                    LocationSettingsRequest.Builder()
                            .addLocationRequest(
                                    LocationRequest.create()
                                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            )
                            .build()
            ).await()
            return locationProvider.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).await()
        } catch (e: SecurityException) {
            throw LocationException.NeedPermissionException
        } catch (e: ResolvableApiException) {
            throw LocationException.DisabledLocationException(e)
        }
    }

}
