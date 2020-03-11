package com.rahulgaur.myapplication.service

import android.app.Instrumentation
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.Keep
import com.rahulgaur.myapplication.R

/**
 * Created by Rahul Gaur on 06,March,2020
 * Email: rahul.gaur152@gmail.com
 */
class PowerButtonService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val mLinear: LinearLayout = object : LinearLayout(applicationContext) {
            //home or recent button
            @Keep
            fun onCloseSystemDialogs(reason: String) {
                when (reason) {
                    "globalactions" -> {
                        Log.e("Key", "Long press on power button")

                        Handler().postDelayed({
                            //this will close all system dialogs after 2sec
                            sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

                            Handler().postDelayed({
                                simulateKey(KeyEvent.KEYCODE_POWER)
                                simulateKey(KeyEvent.KEYCODE_POWER)
                                simulateKey(KeyEvent.KEYCODE_POWER)
                                simulateKey(KeyEvent.KEYCODE_POWER)
                            }, 2000)

                        }, 2000)
                    }
                    "homekey" -> {
                        //home key pressed
                    }
                    "recentapps" -> {
                        // recent apps button clicked
                    }
                }
            }

            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                if (
                    event.keyCode == KeyEvent.KEYCODE_BACK ||
                    event.keyCode == KeyEvent.KEYCODE_VOLUME_UP ||
                    event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ||
                    event.keyCode == KeyEvent.KEYCODE_CAMERA ||
                    event.keyCode == KeyEvent.KEYCODE_POWER
                ) {
                    Log.e("Key", "keycode " + event.keyCode)
                }
                return super.dispatchKeyEvent(event)
            }
        }

        mLinear.isFocusable = true
        val mView: View = LayoutInflater.from(this).inflate(R.layout.layout_service, mLinear)
        val wm =
            getSystemService(Context.WINDOW_SERVICE) as WindowManager

        //params
        val params: WindowManager.LayoutParams =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams(
                    100,
                    100,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
                )
            } else {
                WindowManager.LayoutParams(
                    100,
                    100,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            or WindowManager.LayoutParams.FLAG_FULLSCREEN
                            or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    PixelFormat.TRANSLUCENT
                )
            }
        params.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        wm.addView(mView, params)
    }

    fun simulateKey(keyCode: Int) {
        object : Thread() {
            override fun run() {
                try {
                    Log.e("PowerButtonService", "run: inside simulate key $keyCode")
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(keyCode)
                } catch (e1: Exception) {
                    Log.e("Exception ", e1.toString())
                }
            }
        }.start()
    }
}

// todo: this requires root permission or make my app a system app