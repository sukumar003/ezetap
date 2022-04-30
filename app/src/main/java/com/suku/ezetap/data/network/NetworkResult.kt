package com.suku.ezetap.data.network

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Failure<T>(message: String?) : NetworkResult<T>(null, message)
    class Loading<T>(message: String? = null) : NetworkResult<T>(null, message)
}