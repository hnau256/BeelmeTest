package ru.hnau.beelme.bluetooth.beacon

import android.annotation.TargetApi
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.os.Build
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import ru.hnau.beelme.utils.ContextContainer
import ru.hnau.jutils.finisher.Finisher
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.possible.error
import ru.hnau.jutils.possible.success

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
object BeaconDataTransmitter {

    //Ругался, что не смог стрку "beelme124786testforartem" распарсить в UUID
    private const val BEACON_VALUE_UUID = "a664922e-98bf-4c41-b58e-536c36719897" //"beelme124786testforartem"
    private const val BEACON_VALUE_MINOR = "14"
    private const val BEACON_VALUE_MAJOR = "73"
    private const val BEACON_TX_POWER = -59

    private val BEACON = Beacon.Builder()
            .setId1(BEACON_VALUE_UUID)
            .setId2(BEACON_VALUE_MINOR)
            .setId3(BEACON_VALUE_MAJOR)
            .setTxPower(BEACON_TX_POWER)
            .build()

    private val BEACON_PARSER = BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24")

    private var beaconTransmitter: BeaconTransmitter? = null

    fun start() = synchronized(this) {
        val beaconTransmitter = BeaconTransmitter(ContextContainer.context, BEACON_PARSER)
        BeaconDataTransmitter.beaconTransmitter = beaconTransmitter

        return@synchronized Finisher<Possible<Unit>> { onFinished ->
            beaconTransmitter.startAdvertising(BEACON,
            object : AdvertiseCallback() {

                override fun onStartFailure(errorCode: Int) = onFinished.error("Error code: $errorCode")

                override fun onStartSuccess(settingsInEffect: AdvertiseSettings) = onFinished.success(Unit)

            })
        }


    }

    fun stop() = synchronized(this) {
        beaconTransmitter?.stopAdvertising()
        beaconTransmitter = null
    }

}