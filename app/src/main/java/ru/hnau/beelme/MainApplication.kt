package ru.hnau.beelme

import android.app.Application
import ru.hnau.beelme.utils.ContextContainer


class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ContextContainer.initWithContext(this)
    }

}