package com.fphoenixcorneae.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.fphoenixcorneae.keyboard.R
import com.fphoenixcorneae.keyboard.dp2px
import kotlinx.android.synthetic.main.dk_dialog_alipay.*

/**
 * @desc：支付宝输入支付密码弹窗
 * @date：2020-08-29 11:15
 */
class AlipayDialog constructor(context: Context) : Dialog(context) {

    private var mIsShowAnim: Boolean = false
    private var mIsDismissAnim: Boolean = false

    /**
     * 支付取消监听器
     */
    var onPayCancelListener: (() -> Unit)? = null

    /**
     * 忘记密码监听器
     */
    var onPasswordForgetListener: (() -> Unit)? = null

    /**
     * 密码变化监听器
     */
    var onPasswordChangedListener: ((digitPassword: String, isCompleted: Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dk_dialog_alipay)
        initWindowAttr()
        initListener()
    }

    private fun initWindowAttr() {
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setGravity(Gravity.BOTTOM)
            setDimAmount(.4f)
        }
    }

    private fun initListener() {
        setOnKeyListener { dialog, keyCode, keyEvent ->
            when {
                keyCode == KeyEvent.KEYCODE_BACK && keyEvent.repeatCount == 0 -> {
                    dialog.dismiss()
                    onPayCancelListener?.invoke()
                }
            }
            return@setOnKeyListener false
        }
        ivClose.setOnClickListener {
            dismiss()
            onPayCancelListener?.invoke()
        }
        tvForget.setOnClickListener {
            dismiss()
            onPasswordForgetListener?.invoke()
        }
        rvKeyboard.onPasswordChangedListener = { digitKeyboard, isCompleted ->
            clPassword.setPassword(digitKeyboard)
            if (isCompleted) {
                rvKeyboard.postDelayed({
                    dismiss()
                }, 200)
            }
            onPasswordChangedListener?.invoke(digitKeyboard, isCompleted)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(clPasswordContainer, "alpha", 0.4f, 1f),
                ObjectAnimator.ofFloat(
                    rvKeyboard,
                    "translationY",
                    rvKeyboard.dp2px(300f).toFloat(),
                    0f
                )
            )
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mIsShowAnim = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mIsShowAnim = false
                }
            })
            start()
        }
    }

    override fun dismiss() {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(clPasswordContainer, "alpha", 1f, 0.4f),
                ObjectAnimator.ofFloat(
                    rvKeyboard,
                    "translationY",
                    0f,
                    rvKeyboard.dp2px(300f).toFloat()
                )
            )
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mIsDismissAnim = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mIsDismissAnim = false
                    superDismiss()
                }
            })
            start()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (mIsDismissAnim || mIsShowAnim) {
            true
        } else super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (mIsDismissAnim || mIsShowAnim) {
            return
        }
        super.onBackPressed()
    }

    private fun superDismiss() {
        super.dismiss()
    }

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(false)
    }
}