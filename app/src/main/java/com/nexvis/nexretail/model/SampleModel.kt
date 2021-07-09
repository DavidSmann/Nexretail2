package com.nexvis.nexretail.model

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SampleModel(
    @SerializedName("limit")
    var limit: Int = 0,
    @SerializedName("offset")
    var offset: Int = 0,
    @SerializedName("result")
    var result: List<Result> = listOf(),
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("total")
    var total: Int = 0
)