package com.nexvis.nexretail.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Result(
    @SerializedName("categoryID")
    var categoryID: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("name2L")
    var name2L: String = ""
)