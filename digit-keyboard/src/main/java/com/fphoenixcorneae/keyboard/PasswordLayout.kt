package com.fphoenixcorneae.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach

/**
 * @desc：密码控件
 * @date：2020-08-29 10:20
 */
class PasswordLayout constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {
    /**
     * 设置密码
     */
    fun setPassword(digitPassword: String) {
        if (digitPassword.length > 6) {
            throw ArrayIndexOutOfBoundsException("DigitPassword'length must not be more than 6!")
        }
        resetPassword()
        digitPassword.forEachIndexed { index, char ->
            if (getChildAt(index * 2) is TextView) {
                (getChildAt(index * 2) as TextView).text = char.toString()
            }
        }
    }

    /**
     * 重置密码
     */
    private fun resetPassword() {
        forEach {
            if (it is TextView) {
                it.text = ""
            }
        }
    }

    init {
        setBackgroundResource(R.drawable.dk_shape_bg_password_layout)
        LayoutInflater.from(context).inflate(R.layout.dk_layout_password, this, true)
    }
}