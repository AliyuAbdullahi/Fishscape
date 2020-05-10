package com.example.awesomefish.shared

import android.graphics.Rect

fun eitherOfNumbers(number: Number) =
    number is Int || number is Float || number is Double || number is Long

//Use this for percent of another
infix fun Float.percentOf(another: Float): Float =
    this / 100 * another


//This is just for readablility and why not, it's cool
object of

infix fun Float.percent(of: of): PercentClause {
    return PercentClause(this)
}

class PercentClause(private val param: Float) {
    infix fun number(subject: Float): Float {
        return param / 100 * subject
    }
}


fun String.rect(start: Int, top: Int, height: Int): Rect {
    return Rect(start, top - 100, start + (this.length * height), top + height)
}

