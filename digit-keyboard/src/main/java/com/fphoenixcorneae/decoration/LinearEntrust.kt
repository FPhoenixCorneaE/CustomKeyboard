package com.fphoenixcorneae.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearEntrust(leftRight: Int, topBottom: Int, mColor: Int) :
    SpacesItemDecorationEntrust(leftRight, topBottom, mColor) {
    override fun onDraw(
        c: Canvas?,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        //没有子view或者没有没有颜色直接return
        if (mDivider == null || layoutManager!!.childCount == 0) {
            return
        }
        var left: Int
        var right: Int
        var top: Int
        var bottom: Int
        val childCount = parent.childCount
        if (layoutManager.orientation == GridLayoutManager.VERTICAL) {
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                //将有颜色的分割线处于中间位置
                val center =
                    (layoutManager.getTopDecorationHeight(child) + 1 - topBottom) / 2.toFloat()
                //计算下边的
                left = layoutManager.getLeftDecorationWidth(child)
                right = parent.width - layoutManager.getLeftDecorationWidth(child)
                top = (child.bottom + center).toInt()
                bottom = top + topBottom
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(c!!)
            }
        } else {
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                //将有颜色的分割线处于中间位置
                val center =
                    (layoutManager.getLeftDecorationWidth(child) + 1 - leftRight) / 2.toFloat()
                //计算右边的
                left = (child.right + center).toInt()
                right = left + leftRight
                top = layoutManager.getTopDecorationHeight(child)
                bottom = parent.height - layoutManager.getTopDecorationHeight(child)
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(c!!)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        //竖直方向的
        if (layoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.itemCount - 1) {
                outRect.bottom = topBottom
            }
            outRect.top = topBottom
            outRect.left = leftRight
            outRect.right = leftRight
        } else {
            //最后一项需要right
            if (parent.getChildAdapterPosition(view) == layoutManager.itemCount - 1) {
                outRect.right = leftRight
            }
            outRect.top = topBottom
            outRect.left = leftRight
            outRect.bottom = topBottom
        }
    }
}