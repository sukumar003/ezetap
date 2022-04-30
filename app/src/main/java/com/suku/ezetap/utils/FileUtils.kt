package com.suku.ezetap.utils
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.IOException

object FileUtils {
/*
    fun generateData(context: Context): ArrayList<Claims> {
        val jsonString = getJsonDataFromAsset(context)
        val gson = Gson()
        val claims = object : TypeToken<ClaimData>() {}.type
        val claimsData: ClaimData = gson.fromJson(jsonString, claims)
        return claimsData.claims
    }
*/

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