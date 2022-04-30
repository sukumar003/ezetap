package com.suku.network.data.network

import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.network.ApiService
import com.suku.network.data.network.BaseDataSource
import com.suku.network.data.network.NetworkResult
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiService) :
    BaseDataSource() {

    suspend fun getEmployees(url: String): NetworkResult<DynamicUiData> {
        return invokeApiRequest(apiCall = {
            apiInterface.fetchCustomUI(url)
        })
    }
}