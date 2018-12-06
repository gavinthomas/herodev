package com.gavinthomas.herodatakick.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object DataKickApi {

    fun makeRetrofitService(): DataKickService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl("https://www.datakick.org")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(DataKickService::class.java)
    }

//    override suspend fun fetchFeed(): Response<Feed> = retrofit.create(Api::class.java).getData().await()


//    override fun getItems(): Deferred<Response<List<Product>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

}