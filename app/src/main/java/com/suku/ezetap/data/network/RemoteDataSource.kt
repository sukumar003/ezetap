package com.suku.ezetap.data.network

import com.suku.ezetap.data.model.DynamicUiData
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiService) :
    BaseDataSource() {

    suspend fun getEmployees(url: String): NetworkResult<DynamicUiData> {
        return invokeApiRequest(apiCall = {
            apiInterface.getFormData(url)
        })
    }
}