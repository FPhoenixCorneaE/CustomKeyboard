package com.fphoenixcorneae.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.dialog.AlipayDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnAlipay.setOnClickListener {
            AlipayDialog(this).apply {
                randomDigit = true
            }
                .show()
        }

        digitKeyboard.onPasswordChangedListener = { digitKeyboard, isCompleted ->
            passwordLayout.setPassword(digitKeyboard)
        }
        digitKeyboard.randomDigit = true
    }
}