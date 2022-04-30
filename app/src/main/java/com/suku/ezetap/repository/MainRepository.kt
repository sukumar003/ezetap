package com.suku.ezetap.repository

import com.suku.ezetap.data.model.DynamicUiData
import com.suku.ezetap.data.network.NetworkResult
import com.suku.ezetap.data.network.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    //use coroutine for background thread
    suspend fun getDynamicURLJSON(url: String): NetworkResult<DynamicUiData> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getEmployees(url)
        }
    }
}