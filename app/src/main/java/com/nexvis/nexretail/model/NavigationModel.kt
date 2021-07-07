package com.nexvis.nexretail.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nexvis.nexretail.utils.Constant

class NavigationModel {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("subTitle")
    @Expose
    var subTitle: String? = null
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: Int = 0

    private var typeRow: Constant.DataTypeMenu? = null


    constructor(imageUrl: Int, title: String, typeRow: Constant.DataTypeMenu) {
        this.title = title
        this.imageUrl = imageUrl
        this.typeRow = typeRow
    }

    fun gettypeRow(): Constant.DataTypeMenu? {
        return typeRow
    }

    fun settypeRow(typeRow: Constant.DataTypeMenu) {
        this.typeRow = typeRow
    }
}
