package com.ddrx.ddrxfront.Utilities

import android.content.Context
import android.net.wifi.WifiManager
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


/**
 * Created by dokym on 2018/3/24.
 */
class MacAddressUtil(var context: Context) {

    var macAddress: String? = null
        get() {
            var result1 = getMacAddr()
            if (!result1.isEmpty())
                return result1
            result1 = getMacAddrUnder6()
            if (!result1.isEmpty() && !result1.equals("02:00:00:00:00:00"))
                return result1
            result1 = getMacAddrByInterface()
            if (!result1.isEmpty() && !result1.equals("02:00:00:00:00:00"))
                return result1
            return "02:00:00:00:00:00"
        }

    fun getMacAddr(): String {
        var mac = ""
        try {
            val process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(process.inputStream)
            val input = LineNumberReader(ir)
            var str = ""
            while (str != null) {
                str = input.readLine()
                if (str != null) {
                    mac = str.trim()
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mac
    }

    private fun getMacAddrUnder6(): String {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifiManager.connectionInfo.macAddress
        } catch (e: Exception) {
            return ""
        }
    }

    private fun getMacAddrByInterface(): String {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in interfaces) {
                if (nif.name.equals("wlan0", true)) {
                    val macBytes = nif.hardwareAddress ?: return ""
                    val sb = StringBuilder()
                    for (b in macBytes) {
                        sb.append(String.format("%02X:", b))
                    }
                    if (sb.isNotEmpty())
                        sb.deleteCharAt(sb.length - 1)
                    return sb.toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}