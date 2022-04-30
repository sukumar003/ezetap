package com.suku.ezetap.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.suku.ezetap.R
import com.suku.ezetap.utils.NetworkUtils
import com.suku.ezetap.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callDynamicApi()
        observerData()
    }

    private fun callDynamicApi() {
        if (NetworkUtils.isNetWorkAvailable(applicationContext))
            mainViewModel.callApi()
        else
            showMessage(getString(R.string.no_internet))
    }

    private fun observerData() {
        mainViewModel.dynamicDatas.observe(
            this,
            Observer { Log.d("Sukumar", "DATA:" + Gson().toJson(it)) })
    }
}