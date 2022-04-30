package com.suku.ezetap.data.network

import retrofit2.Response

open class BaseDataSource {
    suspend fun <T> invokeApiRequest(
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        //pass default error msg if u want via constructor
        //var errorMsg = errorMessage
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Failure(errorMessage)
}