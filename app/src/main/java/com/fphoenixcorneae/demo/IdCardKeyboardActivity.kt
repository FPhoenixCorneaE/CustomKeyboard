package com.fphoenixcorneae.demo

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.hideSystemSoftKeyboard
import kotlinx.android.synthetic.main.activity_id_card_keyboard.*

class IdCardKeyboardActivity : AppCompatActivity(R.layout.activity_id_card_keyboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 隐藏系统软键盘
        etIdCardNo.hideSystemSoftKeyboard()
        idCardKeyboard.apply {
            idCardKeyboard.maxLengths = 4
            // 身份证号码变化监听
            onIdCardNoChangedListener = {
                etIdCardNo.text = Editable.Factory.getInstance().newEditable(it)
                etIdCardNo.setSelection(it.length)
            }
        }
    }
}