package com.example.admin.weathershow.ui.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.View


abstract class VHDividerItemDecoration():RecyclerView.ItemDecoration() {
    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val VH = 2
    }

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL] or [.VH].
     */
    private var mOrientation: Int = 0

    private val mBounds = Rect()

    constructor(orientation: Int) : this() {
        setOrientation(orientation)
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL] or [.VH]
     */
    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != VH) {
            throw IllegalArgumentException(
                "Invalid orientation. It should be either HORIZONTAL or VERTICAL")
        }
        mOrientation = orientation
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else if (mOrientation == HORIZONTAL) {
            drawHorizontal(c, parent)
        } else {
            drawVH(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        var decoration = getItemDecoration()
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(child.translationY)
            val top = bottom - decoration.height
            decoration.drawDecorate(canvas, left, top, right, bottom)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        var decoration = getItemDecoration()
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - decoration.width
            decoration.drawDecorate(canvas, left, top, right, bottom)
        }
        canvas.restore()
    }

    private fun drawVH(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
        } else {
            left = 0
            top = 0
            bottom = parent.height
            right = parent.width
        }
        val childCount = parent.childCount
        var decoration = getItemDecoration()
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            //下面的线
            val bottomB = mBounds.bottom + Math.round(child.translationY)
            val topB = bottomB - decoration.height
            decoration.drawDecorate(canvas, left, topB, right, bottomB)
            //上面的线
            val topT = mBounds.top + Math.round(child.translationY)
            val bottomT = topT + decoration.height
            decoration.drawDecorate(canvas, left, topT, right, bottomT)

            //右边的线
            val rightR = mBounds.right + Math.round(child.translationX)
            val leftR = rightR - decoration.width
            decoration.drawDecorate(canvas, leftR, top, rightR, bottom)

            //左边的线
            val leftL = mBounds.left + Math.round(child.translationX)
            val rightL = leftL + decoration.width
            decoration.drawDecorate(canvas, leftL, top, rightL, bottom)

        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        var decoration = getItemDecoration()
        if (decoration == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, decoration.height)
        } else if (mOrientation == HORIZONTAL) {
            outRect.set(0, 0, decoration.width, 0)
        } else {
            outRect.set(0, 0, decoration.width, decoration.height)
        }
    }

    abstract fun getItemDecoration(): Decoration

    class Decoration(val width: Int, val height: Int, @ColorInt val color: Int = Color.GRAY) {
        private var paint = Paint()
        fun drawDecorate(canvas: Canvas, left: Int, top: Int, right: Int, bottom: Int) {
            paint.color = color
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

}