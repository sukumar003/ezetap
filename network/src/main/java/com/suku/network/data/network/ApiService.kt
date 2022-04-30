package com.suku.network.data.network

import com.suku.network.data.model.DynamicUiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun fetchCustomUI(@Url url: String): Response<DynamicUiData>

    @GET
    suspend fun fetchImage(@Url url: String): Response<String>
}