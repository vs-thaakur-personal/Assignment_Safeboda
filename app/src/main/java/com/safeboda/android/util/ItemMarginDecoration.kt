package com.safeboda.android.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ItemMarginDecoration(@DimenRes val leftDimId: Int = 0,
                           @DimenRes val topDimId: Int = 0,
                           @DimenRes val rightDimId: Int = 0,
                           @DimenRes val bottomDimId: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        with(outRect) {
            if (leftDimId != 0) {
                this.left = view.context.resources.getDimensionPixelOffset(leftDimId)
            }
            if (topDimId != 0) {
                this.top = view.context.resources.getDimensionPixelOffset(topDimId)
            }
            if (rightDimId != 0) {
                this.right = view.context.resources.getDimensionPixelOffset(rightDimId)
            }
            if (bottomDimId != 0) {
                this.bottom = view.context.resources.getDimensionPixelOffset(bottomDimId)
            }
        }
    }
}