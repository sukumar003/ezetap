package com.suku.ezetap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.suku.ezetap.repository.MainRepository
import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    var dynamicDatas = MutableLiveData<NetworkResult<DynamicUiData>>()

    fun callApi() {
        viewModelScope.launch {
            dynamicDatas.postValue(NetworkResult.Loading())
            val result: NetworkResult<DynamicUiData> =
                repository.getDynamicURLJSON("mobileapps/android_assignment.json")
            dynamicDatas.postValue(result)
        }
    }
}