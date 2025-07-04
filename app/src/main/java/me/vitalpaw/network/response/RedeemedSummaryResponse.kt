package me.vitalpaw.network.response

import com.google.gson.annotations.SerializedName

data class RedeemedSummaryResponse(
    @SerializedName("totalRedeemed")
    val totalRedeemedCoins: Int

)