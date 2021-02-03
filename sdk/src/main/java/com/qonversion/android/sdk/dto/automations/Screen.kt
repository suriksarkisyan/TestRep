package com.qonversion.android.sdk.dto.automations

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Screen(
    @Json(name = "id") val id: String,
    @Json(name = "body") val htmlPage: String,
    @Json(name = "lang") val lang: String,
    @Json(name = "background") val background: String,
    @Json(name = "object") val obj: String
)
