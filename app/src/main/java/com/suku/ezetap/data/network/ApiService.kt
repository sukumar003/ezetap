package com.suku.ezetap.data.network

import com.suku.ezetap.data.model.DynamicUiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getFormData(@Url url: String): Response<DynamicUiData>
}