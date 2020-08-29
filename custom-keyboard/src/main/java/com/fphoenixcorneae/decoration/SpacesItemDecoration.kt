package com.fphoenixcorneae.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SpacesItemDecoration(private val leftRight: Int, private val topBottom: Int) :
    ItemDecoration() {
    private var mEntrust: SpacesItemDecorationEntrust? = null
    private var mColor = 0

    constructor(leftRight: Int, topBottom: Int, mColor: Int) : this(leftRight, topBottom) {
        this.mColor = mColor
    }

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mEntrust == null) {
            mEntrust = getEntrust(parent.layoutManager)
        }
        mEntrust!!.onDraw(c, parent, state)
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mEntrust == null) {
            mEntrust = getEntrust(parent.layoutManager)
        }
        mEntrust!!.getItemOffsets(outRect, view, parent, state)
    }

    private fun getEntrust(manager: RecyclerView.LayoutManager?): SpacesItemDecorationEntrust {
        //要注意这边的GridLayoutManager是继承LinearLayoutManager，所以要先判断GridLayoutManager
        return when (manager) {
            is GridLayoutManager -> {
                GridEntrust(leftRight, topBottom, mColor)
            }
            is StaggeredGridLayoutManager -> {
                StaggeredGridEntrust(leftRight, topBottom, mColor)
            }
            else -> { //其他的都当做Linear来进行计算
                LinearEntrust(leftRight, topBottom, mColor)
            }
        }
    }

}