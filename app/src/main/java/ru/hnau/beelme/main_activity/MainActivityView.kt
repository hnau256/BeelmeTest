package ru.hnau.beelme.main_activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.*
import ru.hnau.beelme.R
import ru.hnau.beelme.bluetooth.BluetoothStatusManager
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.detacher.ProducerDetachers


class MainActivityView(context: Context) : FrameLayout(context) {

    private val detachers = ProducerDetachers()

    private val innerViewLayoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }


    private val bluetoothIsNotAvailableView: TextView by lazy {
        TextView(context).apply {
            setText(R.string.main_activity_not_available)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
            gravity = Gravity.CENTER
            setTextColor(Color.RED)
            layoutParams = innerViewLayoutParams
        }
    }

    private val bluetoothStatusSwitcher: Switch by lazy {
        object : Switch(context) {

            init {
                setText(R.string.main_activity_status)
                layoutParams = innerViewLayoutParams
            }

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouchEvent(ev: MotionEvent): Boolean {

                if (ev.action == MotionEvent.ACTION_UP) {
                    BluetoothStatusManager.updateStatus(!isChecked)
                }

                return true
            }

        }
    }

    private fun onBluetoothStatusChanged(status: Possible<Boolean>) {
        removeAllViews()
        status.handle(
                onSuccess = {
                    addView(bluetoothStatusSwitcher)
                    bluetoothStatusSwitcher.isChecked = it
                },
                onError = {
                    addView(bluetoothIsNotAvailableView)
                }
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        BluetoothStatusManager.attach(detachers, this::onBluetoothStatusChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detachers.detachAllAndClear()
    }


}