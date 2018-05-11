package ru.hnau.beelme.bluetooth.beacon

import android.os.Build
import ru.hnau.jutils.finisher.Finisher
import ru.hnau.jutils.possible.Possible


object BeaconDataTransmitterWrapper {

    private const val MIN_SUPPORTED_VERSION = Build.VERSION_CODES.LOLLIPOP

    fun start() = doIfSupported(
            supported = BeaconDataTransmitter::start,
            notSupported = { Finisher.forExistenceData(Possible.error("Min supported API is $MIN_SUPPORTED_VERSION")) }
    )

    fun stop() = doIfSupported(
            supported = BeaconDataTransmitter::stop,
            notSupported = { Unit }
    )


    private fun <R> doIfSupported(
            supported: () -> R,
            notSupported: () -> R
    ) =
            if (Build.VERSION.SDK_INT < MIN_SUPPORTED_VERSION)
                notSupported.invoke()
            else
                supported.invoke()


}