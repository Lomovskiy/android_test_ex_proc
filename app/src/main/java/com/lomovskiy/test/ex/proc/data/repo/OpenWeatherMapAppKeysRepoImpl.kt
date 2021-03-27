package com.lomovskiy.test.ex.proc.data.repo

import com.lomovskiy.test.ex.proc.domain.repo.OpenWeatherMapAppKeysRepo

class OpenWeatherMapAppKeysRepoImpl : OpenWeatherMapAppKeysRepo {

    override fun read(): String {
        return "d65a336af4e12371ed6f2c6c4fd1d85d"
    }

}
