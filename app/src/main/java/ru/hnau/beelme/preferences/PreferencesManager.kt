package ru.hnau.beelme.preferences

import ru.hnau.beelme.utils.ContextContainer


object PreferencesManager {

    private val preferences = ContextContainer.context.getSharedPreferences("MAIN_PREFERENCES", 0)

    var bluetoothStatus: Boolean by PreferencesBooleanProperty(preferences, "BLUETOOTH_STATE", false)

}