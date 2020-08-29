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

        digitKeyboard.onPasswordChangedListener = { digitKeyboard, isCompleted ->
            passwordLayout.setPassword(digitKeyboard)
        }
        digitKeyboard.randomDigit = true
    }
}