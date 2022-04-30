package com.suku.ezetap.repository

import com.suku.network.data.network.RemoteDataSource
import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.network.NetworkResult
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