package com.suku.ezetap.utils

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.trim() == "" || value.isEmpty() || value.equals(
        "null",
        ignoreCase = true
    )



