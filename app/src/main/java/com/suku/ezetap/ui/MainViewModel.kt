package com.suku.ezetap.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suku.ezetap.repository.MainRepository
import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    var dynamicDatas: MutableLiveData<DynamicUiData?> = MutableLiveData()

    fun callApi() {
        viewModelScope.launch {
            val result = repository.getDynamicURLJSON("mobileapps/android_assignment.json")
            when (result) {
                is NetworkResult.Success -> dynamicDatas.postValue(result.data)
                is NetworkResult.Failure -> Log.d("ERROR", "" + result.message)
                else -> {
                }
            }
        }
    }
}