package com.suku.ezetap.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.suku.ezetap.R
import com.suku.ezetap.data.network.model.RequestUiData
import com.suku.ezetap.databinding.ActivityMainBinding
import com.suku.ezetap.ui.viewmodel.MainViewModel
import com.suku.ezetap.utils.*
import com.suku.network.data.model.DynamicUiData
import com.suku.network.data.model.UiData
import com.suku.network.data.network.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainBinding: ActivityMainBinding
    private var dynamicUiData: DynamicUiData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
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
        mainViewModel.dynamicDatas.observe(this) { result ->

            when (result) {
                is NetworkResult.Loading -> showProgressBar(true)

                is NetworkResult.Success -> {
                    dynamicUiData = result.data
                    showProgressBar()
                    generateForm()
                }
                is NetworkResult.Failure -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }
                else -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean = false) {
        mainBinding.progressCircular.visibility = if (isVisible)
            View.VISIBLE
        else View.GONE
    }

    private fun generateForm() {
        dynamicUiData?.let { dynamicData ->
            dynamicData.uiData.forEach { uiData ->
                when (uiData.uiType) {
                    WIDGET_TEXTVIEW -> createTextView(uiData)

                    WIDGET_EDIT_TEXT -> createEditText(uiData)

                    WIDGET_BUTTON -> createButton(uiData)
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun createTextView(uiData: UiData) {
        val label =
            layoutInflater.inflate(R.layout.widget_textview, null) as MaterialTextView?
        label?.apply {
            tag = uiData.key
            text = uiData.value
        }
        mainBinding.root.addView(label)
    }

    private fun createEditText(uiData: UiData) {
        val textInputEditText =
            layoutInflater.inflate(R.layout.widget_textinput, null) as TextInputEditText?
        textInputEditText?.apply {
            tag = uiData.key
            hint = uiData.hint ?: ""
            inputType = getInputType(uiData.key)
            isSingleLine = true
        }
        mainBinding.root.addView(textInputEditText)
    }

    @SuppressLint("InflateParams")
    private fun createButton(uiData: UiData) {
        val button =
            layoutInflater.inflate(R.layout.widget_button, null) as MaterialButton?
        button?.apply {
            val mParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            mParams.setMargins(0, 30, 0, 30)
            layoutParams = mParams
            text = uiData.value ?: "Submit"
            setOnClickListener {
                if (validation()) navigateToIntent(getDataFromView())
            }
        }
        mainBinding.root.addView(button)
    }

    private fun getInputType(inputType: String?): Int {
        var type = -1
        when (inputType) {
            INPUT_TYPE_PHONE -> type = InputType.TYPE_CLASS_NUMBER
            INPUT_TYPE_NAME, INPUT_TYPE_CITY -> type =
                InputType.TYPE_CLASS_TEXT
        }
        return type
    }

    private fun validation(): Boolean {
        dynamicUiData?.uiData?.forEach { uiData ->
            when (uiData.uiType) {
                WIDGET_EDIT_TEXT -> {
                    val inputEditText =
                        mainBinding.root.findViewWithTag(uiData.key) as TextInputEditText
                    val value = inputEditText.text.toString()
                    if (isNullOrEmpty(value)) {
                        inputEditText.error = getString(R.string.field_can_not_empty)
                        inputEditText.requestFocus()
                        return false
                    } else if (uiData.key.equals("text_phone", ignoreCase = true)
                        && value.length < 10 || value.length > 10
                    ) {
                        inputEditText.error = getString(R.string.field_invalid)
                        inputEditText.requestFocus()
                        return false
                    } else inputEditText.error = null
                }
            }
        }
        return true
    }

    private fun getDataFromView(): ArrayList<RequestUiData> {
        val requestDataList = ArrayList<RequestUiData>()
        dynamicUiData?.uiData?.forEach { uiData ->
            when (uiData.uiType) {
                WIDGET_EDIT_TEXT -> {
                    val inputEditText =
                        mainBinding.root.findViewWithTag(uiData.key) as TextInputEditText
                    val requestData = RequestUiData(
                        inputEditText.tag.toString(),
                        inputEditText.text.toString()
                    )
                    requestDataList.add(requestData)
                }
            }
        }
        return requestDataList
    }

    private fun navigateToIntent(list: ArrayList<RequestUiData>) {
        val bundle = Bundle()
        bundle.putParcelableArrayList(TAG_DATA, list)
        bundle.putString(TAG_IMAGE_URL, dynamicUiData?.logo_url)
        startActivity(Intent(this, SecondActivity::class.java).putExtras(bundle))
    }

}