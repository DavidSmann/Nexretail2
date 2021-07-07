package com.nexvis.sunfixretail.request

import android.content.Context
import org.json.JSONObject

const val CONTROLLER = "" //store version

object EndPoint {
    const val TERMINAL_LIST = "api/app/terminal"
    const val CONNECT_TOKEN = "connect/token"
    const val FORGOT_PASSWORD = "connect/forgotPassword"
    const val DELETE_LOGIN = "connect/delete"
    const val UPDATE_LOGIN = "connect/update"
    const val ALL_COUNTRY = "rest/v2/all"
    const val PRODUCT_LIST = "api/app/product"
    const val UOM = "api/app/unit-of-measure"
    const val PRODUCT_CATEGORY = "api/app/category"
    const val SALE_ORDER = "api/app/sale-order"
    const val GET_CATEGORY = "api/app/product-api/get-category"
    const val GET_ITEM_BY_CATE_ID = "api/app/product-api/get-item-by-category-id/"
}

fun getTerminalList(context: Context,query: String): GetJSONObjectRequest {
    return GetJSONObjectRequest(context, EndPoint.TERMINAL_LIST)
}

fun userLoginRequest(context: Context, body: JSONObject ):PostJSONObjectRequestLogin {
    return PostJSONObjectRequestLogin(context,EndPoint.CONNECT_TOKEN,body)
}

fun forgotPassword(context: Context, body: JSONObject):PostJSONObjectRequest {
    return PostJSONObjectRequest(context, EndPoint.FORGOT_PASSWORD, body)
}

fun DeleteOrUpdateLogin(context: Context, body: JSONObject):PostJSONObjectRequest {
    return PostJSONObjectRequest(context,EndPoint.DELETE_LOGIN, body)
}

fun UpdateLogin(context: Context, body: JSONObject):PostJSONObjectRequest {
    return PostJSONObjectRequest(context, EndPoint.UPDATE_LOGIN, body)
}

fun getAllCountry(context: Context):GetJSONArrayRequest{
    return GetJSONArrayRequest(context,EndPoint.ALL_COUNTRY)
}

fun getProduct(context: Context):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.PRODUCT_LIST+"?SkipCount=0&MaxResultCount=30&Sorting=0")
}

fun getUOM(context: Context):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.UOM)
}

fun getProductCategory(context: Context):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.PRODUCT_CATEGORY)
}

fun getCategory(context: Context):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.GET_CATEGORY)
}

fun getItemByCateID(context: Context, categoryId: String):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.GET_ITEM_BY_CATE_ID+categoryId)
}

fun getSaleOrderHistory(context: Context):GetJSONObjectRequest{
    return GetJSONObjectRequest(context,EndPoint.SALE_ORDER)
}






