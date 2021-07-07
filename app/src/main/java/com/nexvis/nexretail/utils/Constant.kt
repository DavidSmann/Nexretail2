package com.nexvis.nexretail.utils


object Constant {
    object RequestCode {
        val REQUEST_TAKE_PHOTO = 100
    }

    object IntentKeys {
        const val MOBILE_NUMBER = "MOBILE_NUMBER"
        const val PASSWORD = "PASSWORD"
    }

    enum class DataTypeMenu {
        BOOKNOW,
        SERVENOW,
        PROMOTION,
        ORDERHISTORY,
        SETTING,
        LOGOUT
    }
}