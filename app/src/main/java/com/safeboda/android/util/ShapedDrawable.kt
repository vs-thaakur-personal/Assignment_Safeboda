package com.safeboda.android.util

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

class ShapedDrawable {
    private val drawable: GradientDrawable by lazy { GradientDrawable() }

    fun applyStroke(width: Int, @ColorInt color: Int): ShapedDrawable {
        drawable.setStroke(width, color)
        return this
    }

    fun applyRadius(radius: Float): ShapedDrawable {
        drawable.cornerRadius = radius
        return this
    }

    fun applyShape(shape: Int): ShapedDrawable {
        drawable.shape = shape
        return this
    }

    fun applyBgColor(@ColorInt color: Int): ShapedDrawable {
        drawable.setColor(color)
        return this
    }

    fun getShape() = drawable

}