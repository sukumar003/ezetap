package com.suku.ezetap.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestUiData(
    var key: String?,
    var value: String?
) : Parcelable