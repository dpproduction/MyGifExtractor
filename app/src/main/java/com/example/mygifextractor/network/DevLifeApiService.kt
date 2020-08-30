package com.example.mygifextractor.ui.mygifextractor.network

import com.example.mygifextractor.ui.mygifextractor.network.data.DevLifeObject
import com.example.mygifextractor.ui.mygifextractor.network.data.DevLifeProperty
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://developerslife.ru/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface DevLifeApiService {

    @GET("random?json=true")
    fun getPropertyAsync():
            Deferred<DevLifeProperty>

    @GET("{section}/{page_number}?json=true")
    fun getPropertiesAsync(@Path("section") section: String?, @Path("page_number") page: Int):
            Deferred<DevLifeObject>

}

object DevLifeApi {
    val retrofitService: DevLifeApiService by lazy { retrofit.create(DevLifeApiService::class.java) }
}

