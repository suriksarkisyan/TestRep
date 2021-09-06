package com.qonversion.android.sdk.dto.request

import com.qonversion.android.sdk.dto.Environment
import com.qonversion.android.sdk.dto.purchase.IntroductoryOfferDetails
import com.qonversion.android.sdk.dto.purchase.PurchaseDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseRequest(
    @Json(name = "install_date") override val installDate: Long,
    @Json(name = "device") override val device: Environment,
    @Json(name = "version") override val version: String,
    @Json(name = "access_token") override val accessToken: String,
    @Json(name = "q_uid") override val clientUid: String?,
    @Json(name = "receipt") override val receipt: String = "",
    @Json(name = "debug_mode") override val debugMode: String,
    @Json(name = "purchase") val purchase: PurchaseDetails,
    @Json(name = "introductory_offer") val introductoryOffer: IntroductoryOfferDetails?
) : RequestData()
