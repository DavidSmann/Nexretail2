package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.BookingInteractor
import org.json.JSONObject

class BookingPresenter : CoreImpl<BookingInteractor.View>(), BookingInteractor.Presenter {
    override fun onBooking(body: JSONObject) {
        TODO("Not yet implemented")
    }
}