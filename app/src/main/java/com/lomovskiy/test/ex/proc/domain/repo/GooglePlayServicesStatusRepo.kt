package com.lomovskiy.test.ex.proc.domain.repo

import com.lomovskiy.test.ex.proc.domain.GooglePlayServicesStatus

interface GooglePlayServicesStatusRepo {

    fun read(): GooglePlayServicesStatus

}
