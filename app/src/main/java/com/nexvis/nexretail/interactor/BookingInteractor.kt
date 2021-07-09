package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface BookingInteractor {

    interface View : CoreView {
        fun onBookingResponse(data: JSONObject)
        fun onBookingFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onBooking(body: JSONObject)
    }
}