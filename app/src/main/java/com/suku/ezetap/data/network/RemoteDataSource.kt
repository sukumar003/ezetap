package com.suku.ezetap.data.network

import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.network.ApiService
import com.suku.network.data.network.BaseDataSource
import com.suku.network.data.network.NetworkResult
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiService) :
    BaseDataSource() {

    suspend fun getCustomUiJSON(url: String): NetworkResult<DynamicUiData> {
        return invokeApiRequest(apiCall = {
            apiInterface.fetchCustomUI(url)
        })
    }

    suspend fun getCustomImage(url: String): NetworkResult<String> {
        return invokeApiRequest(apiCall = {
            apiInterface.fetchImage(url)
        })
    }
}