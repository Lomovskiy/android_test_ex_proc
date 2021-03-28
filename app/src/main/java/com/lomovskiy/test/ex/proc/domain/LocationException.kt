package com.lomovskiy.test.ex.proc.domain

import com.google.android.gms.common.api.ResolvableApiException

sealed class LocationException : RuntimeException() {

    object NeedPermissionException : LocationException()
    class DisabledLocationException(val cs: ResolvableApiException) : LocationException()

}
