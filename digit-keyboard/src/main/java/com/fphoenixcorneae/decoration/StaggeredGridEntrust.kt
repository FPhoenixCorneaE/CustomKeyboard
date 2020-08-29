package com.fphoenixcorneae.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridEntrust(leftRight: Int, topBottom: Int, mColor: Int) :
    SpacesItemDecorationEntrust(leftRight, topBottom, mColor) {
    override fun onDraw(
        c: Canvas?,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        //TODO 因为排列的不确定性，暂时没找到比较好的处理方式
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        val layoutManager =
            parent.layoutManager as StaggeredGridLayoutManager?
        val lp =
            view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val childPosition = parent.getChildAdapterPosition(view)
        val spanCount = layoutManager!!.spanCount
        val spanSize = if (lp.isFullSpan) layoutManager.spanCount else 1
        if (layoutManager.orientation == GridLayoutManager.VERTICAL) {
            if (getSpanGroupIndex(childPosition, spanCount, spanSize) == 0) { //第一排的需要上面
                outRect.top = topBottom
            }
            outRect.bottom = topBottom
            //这里忽略和合并项的问题，只考虑占满和单一的问题
            if (lp.isFullSpan) { //占满
                outRect.left = leftRight
                outRect.right = leftRight
            } else {
                outRect.left =
                    ((spanCount - lp.spanIndex).toFloat() / spanCount * leftRight).toInt()
                outRect.right =
                    (leftRight.toFloat() * (spanCount + 1) / spanCount - outRect.left).toInt()
            }
        } else {
            if (getSpanGroupIndex(childPosition, spanCount, spanSize) == 0) { //第一排的需要left
                outRect.left = leftRight
            }
            outRect.right = leftRight
            //这里忽略和合并项的问题，只考虑占满和单一的问题
            if (lp.isFullSpan) { //占满
                outRect.top = topBottom
                outRect.bottom = topBottom
            } else {
                outRect.top =
                    ((spanCount - lp.spanIndex).toFloat() / spanCount * topBottom).toInt()
                outRect.bottom =
                    (topBottom.toFloat() * (spanCount + 1) / spanCount - outRect.top).toInt()
            }
        }
    }

    private fun getSpanGroupIndex(adapterPosition: Int, spanCount: Int, spanSize: Int): Int {
        var span = 0
        var group = 0
        for (i in 0 until adapterPosition) {
            span += spanSize
            if (span == spanCount) {
                span = 0
                group++
            } else if (span > spanCount) {
                // did not fit, moving to next row / column
                span = spanSize
                group++
            }
        }
        if (span + spanSize > spanCount) {
            group++
        }
        return group
    }
}