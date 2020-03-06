package com.rahulgaur.myapplication

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager


/**
 * Created by Rahul Gaur on 06,March,2020
 * Email: rahul@appwebstudios.com
 */
class PowerMenuService : AccessibilityService() {

    private val powerMenuReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (!performGlobalAction(intent.getIntExtra("action", -1))) Toast.makeText(
                context,
                "Not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
    }

    override fun onInterrupt() {
    }

    override fun onCreate() {
        super.onCreate()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(powerMenuReceiver, IntentFilter("com.rahulgaur.myapplication.ACCESSIBILITY_ACTION"))
    }
}