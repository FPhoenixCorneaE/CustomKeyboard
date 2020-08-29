package com.fphoenixcorneae

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresPermission

/**
 * dp转px
 */
fun View.dp2px(dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * Vibrate.
 *
 * Must hold `<uses-permission android:name="android.permission.VIBRATE" />`
 *
 * @param milliseconds The number of milliseconds to vibrate.
 */
@RequiresPermission(Manifest.permission.VIBRATE)
fun View.vibrate(milliseconds: Long = 10) {
    val vibrator =
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    when {
        vibrator.hasVibrator() -> { // 判断手机硬件是否有振动器
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            milliseconds,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }
                else -> {
                    vibrator.vibrate(milliseconds)
                }
            }
        }
    }
}

/**
 * 隐藏系统软键盘
 */
@SuppressLint("ObsoleteSdkInt")
fun EditText.hideSystemSoftKeyboard() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        try {
            val cls = EditText::class.java
            val setShowSoftInputOnFocus =
                cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
            setShowSoftInputOnFocus.isAccessible = true
            setShowSoftInputOnFocus.invoke(this, false)
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        inputType = InputType.TYPE_NULL
    }
}