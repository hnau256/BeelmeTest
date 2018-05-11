package ru.hnau.beelme.utils

import android.annotation.SuppressLint
import android.content.Context


@SuppressLint("StaticFieldLeak")
object ContextContainer {

    private var contextInner: Context? = null

    val context: Context
        get() = contextInner!!

    fun initWithContext(context: Context) {
        contextInner = context
    }

}