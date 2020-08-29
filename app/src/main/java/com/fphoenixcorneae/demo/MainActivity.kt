package com.fphoenixcorneae.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.dialog.AlipayDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnAlipay.setOnClickListener {
            AlipayDialog(this)
                .apply {
                    // 是否随机数字
                    randomDigit = true
                    // 支付取消监听器
                    onPayCancelListener = {

                    }
                    // 忘记密码监听器
                    onPasswordForgetListener = {

                    }
                    // 密码变化监听器
                    onPasswordChangedListener = { digitKeyboard, isCompleted ->

                    }
                }
                .show()
        }

        btnIdCard.setOnClickListener {
            startActivity(Intent(this, IdCardKeyboardActivity::class.java))
        }

        digitKeyboard.onPasswordChangedListener = { digitKeyboard, isCompleted ->
            passwordLayout.setPassword(digitKeyboard)
        }
        digitKeyboard.randomDigit = true
    }
}