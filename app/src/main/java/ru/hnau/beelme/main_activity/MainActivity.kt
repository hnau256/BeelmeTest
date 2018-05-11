package ru.hnau.beelme.main_activity

import android.app.Activity
import android.os.Bundle
import ru.hnau.beelme.bluetooth.BluetoothStatusManager

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BluetoothStatusManager.onMainActivityCreate(this)

        setContentView(MainActivityView(this))

    }

    override fun onDestroy() {

        BluetoothStatusManager.onMainActivityDestroy(this)

        super.onDestroy()

    }


}
