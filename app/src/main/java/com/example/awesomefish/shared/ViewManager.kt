package com.example.awesomefish.shared

import android.app.Activity
import android.util.DisplayMetrics

fun Activity.screenDimension(): WidthHeightPair<Int, Int> {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return Pair(metrics.widthPixels, metrics.heightPixels)
}

object ViewManager {
    var width: Int = 0
    var height: Int = 0
}

enum class ScreenSize(val width: Int, val height: Int) {
    Unknown(0, 0),
    Small(400, 700),
    Medium(800, 1400),
    Large(1500, 2800),
    VeryLarge(2900, 5600);
}

fun getSize(size: Int): ScreenSize {
    val all = listOf(
        ScreenSize.Unknown,
        ScreenSize.Small,
        ScreenSize.Medium,
        ScreenSize.Large,
        ScreenSize.VeryLarge
    )

    return all.first { it.width >= size }
}

typealias WidthHeightPair<A, B> = Pair<A, B>

