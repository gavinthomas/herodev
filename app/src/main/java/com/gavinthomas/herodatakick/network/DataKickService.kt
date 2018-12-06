package com.gavinthomas.herodatakick.network

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface DataKickService {

    @GET("/api/items?query=cheese")
    fun getItems(): Deferred<Response<List<Product>>>

}