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

typealias WidthHeightPair<A, B> = Pair<A, B>

