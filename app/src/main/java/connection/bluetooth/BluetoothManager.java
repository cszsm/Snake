package connection.bluetooth;

import android.bluetooth.BluetoothSocket;

import connection.enumaration.DeviceType;

/**
 * Created by Zsolt on 2015.04.02..
 */
public class BluetoothManager {
    private static BluetoothManager instance = null;

    private BluetoothSocket bluetoothSocket;
    private DeviceType deviceType;

    protected BluetoothManager() {}

    public static BluetoothManager getInstance() {
        if(instance == null)
            instance = new BluetoothManager();
        return instance;
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
