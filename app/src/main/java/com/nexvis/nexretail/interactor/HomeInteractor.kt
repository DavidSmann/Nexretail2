package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface HomeInteractor {
    interface View : CoreView {

    }

    interface Presenter : CorePresenter<View> {

    }
}