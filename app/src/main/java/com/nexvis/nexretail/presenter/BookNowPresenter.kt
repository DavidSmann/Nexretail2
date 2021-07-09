package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.BookNowInteractor
import org.json.JSONObject

class BookNowPresenter : CoreImpl<BookNowInteractor.View>(),
        BookNowInteractor.Presenter {
    override fun onBookNow(body: JSONObject) {
        TODO("Not yet implemented")
    }
}