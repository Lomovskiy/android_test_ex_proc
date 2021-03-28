package com.lomovskiy.test.ex.proc.data

import com.lomovskiy.test.ex.proc.domain.repo.OpenWeatherMapAppKeysRepo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SigningInterceptor @Inject constructor(
    private val openWeatherMapAppKeysRepo: OpenWeatherMapAppKeysRepo
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.header("Authorization") != null) {
            request = request.newBuilder()
                .removeHeader("Authorization")
                .url(
                    request.url()
                        .newBuilder()
                        .addQueryParameter("appid", openWeatherMapAppKeysRepo.read())
                        .build()
                )
                .build()
        }
        return chain.proceed(request)
    }

}