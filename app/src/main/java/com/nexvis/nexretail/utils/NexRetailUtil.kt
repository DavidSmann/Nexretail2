package com.nexvis.nexretail.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.math.RoundingMode
import java.net.NetworkInterface
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object NexRetailUtil {

    fun convertDpToPx(context: Context, dp: Int): Int {
        return Math.round(dp * context.resources.displayMetrics.density)
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int, displayMetrics: DisplayMetrics): Int = (dp * displayMetrics.density).toInt()

    fun isNumeric(strNum: String): Boolean {
        try {
            val d = strNum.replace(" ", "").toDouble()
        } catch (nfe: NumberFormatException) {
            return false
        }
        return true
    }

    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private val c = charArrayOf('k', 'm', 'b', 't')

    fun formatNumberK(n: Double, iteration: Int = 0): String? {
        if (n.isNaN()) {
            return "0"
        }
        if (n < 1000) {
            return formatDecimalNumber(n, "#.##")//n.toFix(2)
        }
        val d = n.toLong() / 100 / 10.0
        val isRound = d * 10 % 10 == 0.0//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (if (d < 1000)
        //this determines the class, i.e. 'k', 'm' etc
            ((if (d > 99.9 || isRound || (!isRound && d > 9.99))
            //this decides whether to trim the decimals
                d.toInt() * 10 / 10
            else
                (d).toString() + "" // (int) d * 10 / 10 drops the decimal
                    )).toString() + "" + c[iteration]
        else
            formatNumberK(d, iteration + 1))?.toUpperCase()
    }

    fun getDisplayMetrics(context: Activity): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        context.windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)

        return displayMetrics
    }

    fun readableFileSize(size: Long): String {
        if (size <= 0) return "0 Bytes"
        val units = arrayOf("Bytes", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isEmailMustBeEmail(email: String): Array<String> {
        return email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

//    fun showSessionExpired(context: Context) {
//        if (context !is SplashScreenActivity) {
//            VIPAlertDialog.with(context, context.getString(R.string.message_login_expired))
//                    .setPositiveButton(context.getString(R.string.log_out), DialogInterface.OnClickListener { _, _ ->
//                        if (context is BaseActivity) {
//                            FirebaseAuth.getInstance().signOut()
//                            VipSharePreferences.getConstant(context).clearAll()
//                            LoginActivity.launchClearTask(context)
//                        }
//                    }).show()
//        }
//    }

    fun getLocalTimezone(): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        return tz.id
    }

    fun getTimestamp(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val tz = TimeZone.getTimeZone("UTC")
        df.timeZone = tz
        return df.format(Date())
    }

    fun getCurrentDateTime(outputFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String {
        val df = SimpleDateFormat(outputFormat, Locale.getDefault())
        df.timeZone = Calendar.getInstance().timeZone
        return df.format(Date())
    }

    fun getCurrentTime(outputFormat: String = "'T'HH:mm:ss.SSS'Z'"): String {
        val df = SimpleDateFormat(outputFormat, Locale.getDefault())
        df.timeZone = Calendar.getInstance().timeZone
        return df.format(Date())
    }

    fun getCurrentDateTimeUTC(outputFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String {
        val df = SimpleDateFormat(outputFormat, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        return df.format(Date())
    }

    fun getIPAddress(useIPv4: Boolean = true): String {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(0, delim).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (ignored: Exception) {
        }
        // for now eat exceptions
        return ""
    }

    fun formatDecimalNumber(value: Double, pattern: String, roundModeCeiling: Boolean = true): String {
        val otherSymbols = DecimalFormatSymbols(Locale.getDefault())
        otherSymbols.decimalSeparator = '.'
        val decimalFormat = DecimalFormat(pattern, otherSymbols)
        if (roundModeCeiling)
            decimalFormat.roundingMode = RoundingMode.CEILING
        return decimalFormat.format(value)
    }

    fun getLastMonth(monthAgo: Int, isSet: Boolean = false): String {
        val calendar = Calendar.getInstance()
        if (isSet)
            calendar.set(Calendar.MONTH, monthAgo)
        else calendar.add(Calendar.MONTH, -monthAgo)
        return SimpleDateFormat("MMM").format(calendar.timeInMillis)
    }

    fun getNextMonth(monthAgo: Int, isSet: Boolean = false): String {
        val calendar = Calendar.getInstance()
        if (isSet)
            calendar.set(Calendar.MONTH, monthAgo)
        else calendar.add(Calendar.MONTH, +monthAgo)
        return SimpleDateFormat("MMM").format(calendar.timeInMillis)
    }

    fun getMonthForInt(num: Int): String {
        var month = ""
        val dfs = DateFormatSymbols()
        val months = dfs.months
        if (num in 0..11) {
            month = months[num]
        }
        return if (month.length > 3) month.substring(0, 3) else month
    }

}