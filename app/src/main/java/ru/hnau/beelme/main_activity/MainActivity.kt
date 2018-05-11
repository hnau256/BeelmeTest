package ru.hnau.beelme.main_activity

import android.app.Activity
import android.os.Bundle
import ru.hnau.beelme.utils.MainActivityConnector

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityConnector.onMainActivityCreated(this)
        setContentView(MainActivityView(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivityConnector.onMainActivityDestroyed()
    }


}
