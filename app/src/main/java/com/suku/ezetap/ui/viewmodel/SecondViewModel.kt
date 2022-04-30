package com.suku.ezetap.ui.viewmodel

import com.suku.ezetap.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {
}