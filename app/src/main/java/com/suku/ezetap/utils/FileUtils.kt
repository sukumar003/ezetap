package com.suku.ezetap.utils

import android.content.Context
import java.io.IOException

object FileUtils {

    private fun getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("claims_data.json").bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}