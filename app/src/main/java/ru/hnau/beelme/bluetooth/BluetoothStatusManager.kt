package ru.hnau.beelme.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ru.hnau.beelme.preferences.PreferencesManager
import ru.hnau.beelme.utils.MainActivityConnector
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.CallOnAttachProducer


object BluetoothStatusManager : CallOnAttachProducer<Possible<Boolean>>() {

    private const val ENABLE_ACTION = "android.bluetooth.adapter.action.REQUEST_ENABLE"
    private const val DISABLE_ACTION = "android.bluetooth.adapter.action.REQUEST_DISABLE"

    private val STATUS_CHANGED_LISTENER_INTENT_FILTER = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

    private val statusChangedListener = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            intent.action?.takeIf { it == BluetoothAdapter.ACTION_STATE_CHANGED } ?: return
            onBluetoothStatusChanged()
        }

    }

    private val bluetoothStatus: Possible<Boolean>
        get() = Possible.trySuccess(BluetoothAdapter.getDefaultAdapter()?.isEnabled)

    private fun onBluetoothStatusChanged() {
        val bluetoothStatus = bluetoothStatus
        PreferencesManager.bluetoothStatus = bluetoothStatus.data == true
        call(bluetoothStatus)
    }

    fun updateStatus(status: Boolean) {
        if (bluetoothStatus.data == null || bluetoothStatus.data == status) {
            return
        }
        val intent = Intent(if (status) ENABLE_ACTION else DISABLE_ACTION)
        MainActivityConnector.mainActivity?.startActivity(intent)
    }

    override fun getDataForAttachedListener(listener: (Possible<Boolean>) -> Unit) = bluetoothStatus

    override fun onFirstAttached() {
        super.onFirstAttached()
        MainActivityConnector.mainActivity?.registerReceiver(statusChangedListener, STATUS_CHANGED_LISTENER_INTENT_FILTER)
        updateStatusToLastSessionValue()
    }

    override fun onLastDetached() {
        super.onLastDetached()
        MainActivityConnector.mainActivity?.unregisterReceiver(statusChangedListener)
    }

    private fun updateStatusToLastSessionValue() {
        val currentStatus = bluetoothStatus.data ?: return
        val statusFromPreferences = PreferencesManager.bluetoothStatus
        if (currentStatus == statusFromPreferences) {
            return
        }
        updateStatus(statusFromPreferences)
    }


}