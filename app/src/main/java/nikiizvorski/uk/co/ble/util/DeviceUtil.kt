package nikiizvorski.uk.co.ble.util

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*

class DeviceUtil {

    /**
     * Gets device make.
     *
     * @return the device make
     */
    fun getDeviceMake(): String {
        return Build.MANUFACTURER
    }

    /**
     * Gets device model.
     *
     * @return the device model
     */
    fun getDeviceModel(): String {
        return Build.MODEL
    }


    /**
     * Gets device os.
     *
     * @return the device os
     */
    fun getDeviceOS(): String {
        return Build.VERSION.RELEASE
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    fun getCountry(): String {
        return Locale.getDefault().country
    }

    /**
     * Is wifi enabled boolean.
     *
     * @param context the context
     * @return the boolean
     */
    fun isWifiEnabled(context: Context): Boolean {
        if (!context.packageManager.hasSystemFeature("android.hardware.wifi")) {
            return false
        }
        val wifiManager = context.applicationContext.getSystemService("wifi") as WifiManager
        return wifiManager != null && wifiManager.isWifiEnabled
    }

    /**
     * Gets time zone offset.
     *
     * @return the time zone offset
     */
    fun getTimeZoneOffset(): String {
        val timezone = Calendar.getInstance().timeZone
        var offset = timezone.rawOffset

        if (timezone.inDaylightTime(Date())) {
            offset += timezone.dstSavings
        }
        return (offset / 1000).toString()
    }

    /**
     * Get package name string.
     *
     * @param context the context
     * @return the string
     */
    fun getPackageName(context: Context): String {
        return context.packageName
    }

    /**
     * Apply sha 256 string.
     *
     * @param input the input
     * @return the string
     */
    fun applySha256(input: String): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            //Applies sha256 to our input,
            val hash = digest.digest(input.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer() // This will contain hash as hexidecimal
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            return hexString.toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Is valid json boolean.
     *
     * @param toTestStr the to test str
     * @return the boolean
     */
    fun isValidJSON(toTestStr: String): Boolean {
        try {
            JSONObject(toTestStr)
        } catch (jsExcp: JSONException) {
            try {
                JSONArray(toTestStr)
            } catch (jsExcp1: JSONException) {
                return false
            }

        }

        return true
    }
}