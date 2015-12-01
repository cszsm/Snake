package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

import connection.ConnectionProperties;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 * <p/>
 * Connects to another device, which run a BluetoothAcceptThread
 */
public class BluetoothConnectThread extends Thread {

    private final BluetoothSocket bluetoothSocket;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothActivity activity;

    public BluetoothConnectThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothDevice bluetoothDevice,
                                  BluetoothActivity activity) {
        BluetoothSocket tmp = null;
        this.bluetoothAdapter = bluetoothAdapter;
        this.activity = activity;

        try {
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bluetoothSocket = tmp;
        ConnectionProperties.getInstance().setDeviceType(DeviceType.CLIENT);
    }

    /**
     * Connects another device via the socket
     */
    @Override
    public void run() {
        bluetoothAdapter.cancelDiscovery();

        try {
            bluetoothSocket.connect();
        } catch (IOException connectException) {
            try {
                connectException.printStackTrace();
                bluetoothSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
            return;
        }

        ConnectionProperties.getInstance().setSocket(new BluetoothConnectionSocket(bluetoothSocket));
        activity.startGame();
//        activity.startSynchronizer();
//        activity.startTest();
    }
}
