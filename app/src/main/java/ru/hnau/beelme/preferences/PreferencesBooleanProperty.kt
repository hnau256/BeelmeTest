package ru.hnau.beelme.preferences

import android.content.SharedPreferences


class PreferencesBooleanProperty(
        preferences: SharedPreferences,
        key: String,
        defaultValue: Boolean = false
) : PreferencesProperty<Boolean>(
        preferences,
        key,
        defaultValue
) {

    override fun readValue(container: SharedPreferences, key: String) = container.getBoolean(key, defaultValue)

    override fun writeValue(editor: SharedPreferences.Editor, key: String, value: Boolean) {
        editor.putBoolean(key, value)
    }
}