package ru.hnau.beelme.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


abstract class PreferencesProperty<T>(
        private val preferences: SharedPreferences,
        private val key: String,
        val defaultValue: T
) : ReadWriteProperty<Any, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        var result = value
        if (value == null) {
            result = readValue(preferences, key)
            value = result
        }

        return result ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = synchronized(this, {
        if (value == this.value) {
            return
        }
        this.value = value
        val editor = preferences.edit()
        writeValue(editor, key, value)
        editor.apply()
        return@synchronized
    })

    abstract fun readValue(container: SharedPreferences, key: String): T

    abstract fun writeValue(editor: SharedPreferences.Editor, key: String, value: T)

}