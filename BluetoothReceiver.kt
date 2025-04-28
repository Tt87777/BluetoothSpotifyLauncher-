package com.example.bluetoothlauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.bluetooth.BluetoothDevice
import android.net.Uri

class BluetoothReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == BluetoothDevice.ACTION_ACL_CONNECTED) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if (device?.bondState == BluetoothDevice.BOND_BONDED) {
                launchSpotify(context)
            }
        }
    }

    private fun launchSpotify(context: Context) {
        try {
            val spotifyIntent = context.packageManager.getLaunchIntentForPackage("com.spotify.music")
            spotifyIntent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            } ?: run {
                val marketIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=com.spotify.music")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(marketIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
