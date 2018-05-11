package ru.hnau.beelme.utils

import ru.hnau.beelme.main_activity.MainActivity


object MainActivityConnector {

    var mainActivity: MainActivity? = null
        private set

    fun onMainActivityCreated(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    fun onMainActivityDestroyed() {
        mainActivity = null
    }


}