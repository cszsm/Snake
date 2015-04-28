package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import connection.enumaration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothActivity activity;

    public ConnectThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothDevice bluetoothDevice, BluetoothActivity activity) {
        BluetoothSocket tmp = null;
        this.bluetoothAdapter = bluetoothAdapter;
        this.activity = activity;

        try {
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {

        }
        bluetoothSocket = tmp;
    }

    public void run() {
        bluetoothAdapter.cancelDiscovery();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Connecting server", Toast.LENGTH_LONG).show();
            }
        });

        try {
            bluetoothSocket.connect();
        } catch (IOException connectException) {
            Log.v("tag", "ConnectThread - Unable to connect");
            try {
                bluetoothSocket.close();
            } catch (IOException closeException) {
                Log.v("tag", "ConnectThread - Bluetooth socket connection exception");
            }
            return;
        }

        BluetoothManager.getInstance().setBluetoothSocket(bluetoothSocket);
        BluetoothManager.getInstance().setDeviceType(DeviceType.SLAVE);
        activity.startGame();
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {

        }
    }
}
