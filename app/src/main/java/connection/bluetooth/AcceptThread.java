package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import connection.enumaration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 */
public class AcceptThread extends Thread {
    private final BluetoothServerSocket bluetoothServerSocket;
    private BluetoothActivity activity;

    public AcceptThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothActivity activity) {
        BluetoothServerSocket tmp = null;
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Snake", uuid);
        } catch (IOException e) {

        }
        bluetoothServerSocket = tmp;

        this.activity = activity;
    }

    public void run() {
        BluetoothSocket bluetoothSocket = null;
        while (true) {
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                break;
            }

            if (bluetoothSocket != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Server started", Toast.LENGTH_LONG).show();
                    }
                });
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {

                }

                BluetoothManager.getInstance().setBluetoothSocket(bluetoothSocket);
                BluetoothManager.getInstance().setDeviceType(DeviceType.MASTER);
                activity.startGame();

                break;
            }
        }
    }

    public void cancel() {
        try {
            bluetoothServerSocket.close();
        } catch (IOException e) {

        }
    }
}
