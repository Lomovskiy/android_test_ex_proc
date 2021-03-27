package com.lomovskiy.test.ex.proc.data

import com.lomovskiy.test.ex.proc.data.repo.OpenWeatherMapAppKeysRepoImpl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

internal const val REQUIRE_HEADER_API_KEY = "Authorization: true"
internal const val ENDPOINT = "https://api.openweathermap.org/data/2.5/"
internal const val IMAGE_PATH_TEMPLATE = "http://openweathermap.org/img/wn/%s@2x.png"


val remoteApi: RemoteApi = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(SigningInterceptor(OpenWeatherMapAppKeysRepoImpl()))
            .build()
    )
    .baseUrl(ENDPOINT)
    .build()
    .create(RemoteApi::class.java)

interface RemoteApi {

    @Headers(REQUIRE_HEADER_API_KEY)
    @POST("onecall")
    fun getWeatherSnapshotByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): Call<ResponseBody>

}
