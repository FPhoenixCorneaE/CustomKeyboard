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
 * @desc：身份证键盘
 * @date：2020-08-29 17:37
 */
class IdCardKeyboardView constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : RecyclerView(context, attributeSet) {

    /**
     * 数据
     */
    var datas = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "0")

    /**
     * 适配器
     */
    private var idCardKeyboardAdapter: IdCardKeyboardAdapter = IdCardKeyboardAdapter(datas)

    /**
     * 身份证号码变化监听器
     */
    var onIdCardNoChangedListener: ((idCardNo: String) -> Unit)? = null
        set(value) {
            field = value
            idCardKeyboardAdapter.onIdCardNoChangedListener = value
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
        adapter = idCardKeyboardAdapter
    }
}

/**
 * @desc：身份证键盘适配器
 * @date：2020-08-29 17:38
 */
class IdCardKeyboardAdapter(val datas: MutableList<String> = mutableListOf()) :
    RecyclerView.Adapter<IdCardKeyboardViewHolder>() {

    private var idCardNo = ""
    var onIdCardNoChangedListener: ((idCardNo: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdCardKeyboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dk_recycler_item_digit_keyboard, parent, false)
        return IdCardKeyboardViewHolder(itemView)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: IdCardKeyboardViewHolder, position: Int) {
        holder.apply {
            when (position) {
                11 -> {
                    // 删除键
                    itemView.setBackgroundResource(R.drawable.dk_selector_keyboard_key_delete_bg)
                    tvKey.visibility = View.GONE
                    ivDelete.visibility = View.VISIBLE
                    itemView.setOnClickListener {
                        it.vibrate()
                        if (idCardNo.isNotEmpty()) {
                            idCardNo = idCardNo.dropLast(1)
                            onIdCardNoChangedListener?.invoke(idCardNo)
                        }
                    }
                    itemView.setOnLongClickListener {
                        if (idCardNo.isNotEmpty()) {
                            idCardNo = idCardNo.dropLast(1)
                            onIdCardNoChangedListener?.invoke(idCardNo)
                        }
                        return@setOnLongClickListener false
                    }
                }
                else -> {
                    // 数字键
                    itemView.setBackgroundResource(R.drawable.dk_selector_keyboard_key_digit_bg)
                    tvKey.visibility = View.VISIBLE
                    ivDelete.visibility = View.GONE
                    val no = datas[position]
                    tvKey.text = no
                    itemView.setOnClickListener {
                        it.vibrate()
                        if (idCardNo.length < 18) {
                            idCardNo = "$idCardNo$no"
                            onIdCardNoChangedListener?.invoke(idCardNo)
                        }
                    }
                }
            }
        }
    }
}

/**
 * @desc：身份证键盘ViewHolder
 * @date：2020-08-29 17:39
 */
class IdCardKeyboardViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer

