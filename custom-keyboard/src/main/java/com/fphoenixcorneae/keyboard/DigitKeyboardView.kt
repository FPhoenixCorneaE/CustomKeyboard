package com.fphoenixcorneae.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.decoration.SpacesItemDecoration
import com.fphoenixcorneae.dp2px
import com.fphoenixcorneae.vibrate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dk_recycler_item_digit_keyboard.*

/**
 * @desc：数字键盘
 * @date：2020-08-28 15:06
 */
class DigitKeyboardView constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : RecyclerView(context, attributeSet) {

    /**
     * 是否随机数字
     */
    var randomDigit = false
        set(value) {
            field = value
            shuffleData()
        }

    /**
     * 最大长度,默认为6
     */
    var maxLengths = 6
        set(value) {
            field = value
            digitKeyboardAdapter?.maxLengths = field
        }

    /**
     * 数据
     */
    var datas = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
        set(value) {
            if (value.size != 10) {
                throw ArrayIndexOutOfBoundsException("Datas'size must be 10!")
            }
            field = value
            shuffleData()
        }

    /**
     * 适配器
     */
    private var digitKeyboardAdapter: DigitKeyboardAdapter? = DigitKeyboardAdapter(datas)

    /**
     * 密码变化监听器
     */
    var onPasswordChangedListener: ((digitPassword: String, isCompleted: Boolean) -> Unit)? = null
        set(value) {
            field = value
            digitKeyboardAdapter?.onPasswordChangedListener = value
        }

    private fun shuffleData() {
        if (randomDigit) {
            // 此方法是打乱顺序
            datas.shuffle()
        }
        digitKeyboardAdapter?.notifyDataSetChanged()
    }

    init {
        // 滚动模式
        overScrollMode = View.OVER_SCROLL_NEVER
        // 不允许嵌套滚动
        isNestedScrollingEnabled = false
        // 网格布局管理器
        layoutManager = GridLayoutManager(context, 3)
        // 分割线
        addItemDecoration(
            SpacesItemDecoration(
                dp2px(1f),
                dp2px(1f),
                ContextCompat.getColor(context, R.color.dk_color_0xe5e5e5)
            )
        )
        adapter = digitKeyboardAdapter
    }
}

/**
 * @desc：数字键盘适配器
 * @date：2020-08-29 09:11
 */
class DigitKeyboardAdapter(val datas: MutableList<Int> = mutableListOf()) :
    RecyclerView.Adapter<DigitKeyboardViewHolder>() {

    /**
     * 最大长度,默认为6
     */
    var maxLengths = 6
    private var digitPassword = ""
    var onPasswordChangedListener: ((digitPassword: String, isCompleted: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigitKeyboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dk_recycler_item_digit_keyboard, parent, false)
        return DigitKeyboardViewHolder(itemView)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: DigitKeyboardViewHolder, position: Int) {
        holder.apply {
            when (position) {
                9 -> {
                    // 空白键
                    itemView.setBackgroundResource(R.drawable.dk_selector_keyboard_key_blank_bg)
                    tvKey.visibility = View.GONE
                    ivDelete.visibility = View.GONE
                }
                11 -> {
                    // 删除键
                    itemView.setBackgroundResource(R.drawable.dk_selector_keyboard_key_delete_bg)
                    tvKey.visibility = View.GONE
                    ivDelete.visibility = View.VISIBLE
                    itemView.setOnClickListener {
                        it.vibrate()
                        if (digitPassword.isNotEmpty()) {
                            digitPassword = digitPassword.dropLast(1)
                            onPasswordChangedListener?.invoke(digitPassword, false)
                        }
                    }
                }
                else -> {
                    // 数字键
                    itemView.setBackgroundResource(R.drawable.dk_selector_keyboard_key_digit_bg)
                    tvKey.visibility = View.VISIBLE
                    ivDelete.visibility = View.GONE
                    val digit = when (position) {
                        10 -> datas[position - 1]
                        else -> datas[position]
                    }
                    tvKey.text = digit.toString()
                    itemView.setOnClickListener {
                        it.vibrate()
                        if (digitPassword.length < maxLengths) {
                            digitPassword = "$digitPassword$digit"
                            onPasswordChangedListener?.invoke(
                                digitPassword,
                                digitPassword.length == maxLengths
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * @desc：数字键盘ViewHolder
 * @date：2020-08-29 09:12
 */
class DigitKeyboardViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer

