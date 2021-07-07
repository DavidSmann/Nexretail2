package com.nexvis.nexretail.custom

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.nexvis.nexretail.R


class NexRetailAlertDialog(private val mContext: Context, private var message: String) {

    private val alertDialogBuilder: androidx.appcompat.app.AlertDialog.Builder
    private var mCancelable: Boolean = true
    private var positiveButton: String? = null
    private var negativeButton: String? = null
    private var positiveClickListener: DialogInterface.OnClickListener? = null
    private var negativeClickListener: DialogInterface.OnClickListener? = null
    private var dismissListener: DialogInterface.OnDismissListener? = null
    private var alertDialog: androidx.appcompat.app.AlertDialog? = null

    init {
        positiveButton =mContext.getString(R.string.ok)
        alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(mContext)
        if (TextUtils.equals(message.toLowerCase(), "there is a connection issue")
                || TextUtils.equals(message.toLowerCase(), "internal server error"))
            message = mContext.getString(R.string.cannot_connect)
        alertDialogBuilder.setMessage(message)
    }

    val isShowing get() = alertDialog?.isShowing

    fun dismissDialog() {
        if (alertDialog != null)
            alertDialog!!.dismiss()
        hasDialogShowing = false

    }


    fun show(): NexRetailAlertDialog {

        alertDialogBuilder.setCancelable(getCancelable())
        alertDialogBuilder.setPositiveButton(positiveButton, positiveClickListener)
        if (negativeClickListener != null)
            alertDialogBuilder.setNegativeButton(negativeButton, negativeClickListener)
        if (dismissListener != null) alertDialogBuilder.setOnDismissListener(dismissListener)

        if (alertDialog == null)
            alertDialog = alertDialogBuilder.create()
        alertDialog?.window?.attributes?.windowAnimations = R.style.DialogAnimationScaleInOut
        alertDialog?.dismiss()
        hasDialogShowing = false
        alertDialog?.setOnDismissListener {
            dismissListener?.onDismiss(alertDialog)
            hasDialogShowing = false
        }
        if (!(mContext as Activity).isDestroyed && !alertDialog!!.isShowing && !hasDialogShowing) {
            alertDialog?.show()
            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isAllCaps = false
            alertDialog?.getButton(AlertDialog.BUTTON_NEGATIVE)?.isAllCaps = false
            hasDialogShowing = true
        }

        return this
    }


    fun setPositiveButton(text: String, clickListener: DialogInterface.OnClickListener): NexRetailAlertDialog {
        positiveButton = text
        positiveClickListener = clickListener
        return this
    }

    fun setNegativeButton(text: String, clickListener: DialogInterface.OnClickListener): NexRetailAlertDialog {
        negativeButton = text
        negativeClickListener = clickListener
        return this
    }

    fun setDismiss(dismissListener: DialogInterface.OnDismissListener): NexRetailAlertDialog {
        this.dismissListener = dismissListener
        return this
    }

    fun setCancelable(cancelable: Boolean): NexRetailAlertDialog {
        mCancelable = cancelable
        return this
    }

    private fun getCancelable(): Boolean {
        return mCancelable
    }

    companion object {
        private var hasDialogShowing = false
        fun with(context: Context, message: String): NexRetailAlertDialog {
            return NexRetailAlertDialog(context, message)
        }
    }
}
