package com.lomovskiy.test.ex.proc.data.repo

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.lomovskiy.test.ex.proc.domain.GooglePlayServicesStatus
import com.lomovskiy.test.ex.proc.domain.repo.GooglePlayServicesStatusRepo

class GooglePlayServicesStatusRepoImpl(
    private val context: Context
) : GooglePlayServicesStatusRepo {

    override fun read(): GooglePlayServicesStatus {
        return when (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)) {
            ConnectionResult.SUCCESS -> GooglePlayServicesStatus.AVAILABLE
            else -> GooglePlayServicesStatus.UNAVAILABLE
        }
    }

}
