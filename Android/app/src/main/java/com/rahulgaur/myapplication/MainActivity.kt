package com.rahulgaur.myapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private var isPass: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            if (!isPass) {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.layout_passcode_dialog)

                val editText = dialog.findViewById<TextView>(R.id.passCodeET)
                val btn = dialog.findViewById<TextView>(R.id.passCodeBtn)

                val correctPass = "2222"

                btn.setOnClickListener {

                    val passCode = editText.text.toString()

                    if (passCode == correctPass) {
                        Toast.makeText(this, "Password is correct", Toast.LENGTH_SHORT).show()
                        isPass = true
                    } else {
                        Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                    dialog.show()
                }
                return false
            } else if (isPass) {
                return super.onKeyLongPress(keyCode, event)
            } else {
                return true
            }
        } else {
            return super.onKeyLongPress(keyCode, event)
        }
    }
}
