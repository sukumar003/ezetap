package com.suku.ezetap.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.textview.MaterialTextView
import com.suku.ezetap.R
import com.suku.ezetap.data.network.model.RequestUiData
import com.suku.ezetap.databinding.ActivitySecondBinding
import com.suku.ezetap.ui.viewmodel.SecondViewModel
import com.suku.ezetap.utils.TAG_DATA
import com.suku.ezetap.utils.TAG_IMAGE_URL
import com.suku.ezetap.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private val secondViewModel: SecondViewModel by viewModels()

    private lateinit var mainBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        //show image
        intent.getStringExtra(TAG_IMAGE_URL)?.let {
            mainBinding.imageView.loadImage(it)
        }
        //show result data
        generateForm(intent.getParcelableArrayListExtra(TAG_DATA))
    }

    private fun generateForm(data: ArrayList<RequestUiData>?) {
        data?.let { dynamicData ->
            dynamicData.forEach { uiData ->
                createTextView(uiData)
            }
        }
    }

    private fun createTextView(uiData: RequestUiData) {
        val label = layoutInflater.inflate(R.layout.widget_textview, null) as MaterialTextView?
        label?.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        label?.apply {
            tag = uiData.key
            text = uiData.value
        }
        mainBinding.rootSecond.addView(label)
    }
}
