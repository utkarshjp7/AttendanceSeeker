package cis470.attendanceseeker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cis470.attendanceseeker.entities.DeviceInfo;

public class BluetoothReceiver extends BroadcastReceiver {

    private BluetoothListener mBTListener;
    public BluetoothReceiver(BluetoothListener btListener) {
        this.mBTListener = btListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String name = device.getName();
            int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
            String macAddress = device.getAddress();
            if(name != null) {
                mBTListener.onDetectDevice(new DeviceInfo(name, macAddress, rssi));
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            mBTListener.onFinishDiscovery();
        }
    }

    public interface BluetoothListener {
        void onDetectDevice(DeviceInfo deviceInfo);
        void onFinishDiscovery();
    }
}