package com.fphoenixcorneae.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SpacesItemDecorationEntrust(
    protected var leftRight: Int,
    protected var topBottom: Int,
    mColor: Int
) {
    /**
     * color的传入方式是resources.getColor
     */
    protected var mDivider: Drawable? = null
    abstract fun onDraw(
        c: Canvas?,
        parent: RecyclerView,
        state: RecyclerView.State?
    )

    abstract fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State?
    )

    init {
        if (mColor != 0) {
            mDivider = ColorDrawable(mColor)
        }
    }
}