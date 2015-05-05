package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 *
 * Waits for another device to connect
 */
public class BluetoothAcceptThread extends Thread {
    private final BluetoothServerSocket bluetoothServerSocket;
    private BluetoothActivity activity;

    public BluetoothAcceptThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothActivity activity) {
        BluetoothServerSocket tmp = null;
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Snake", uuid);
        } catch (IOException e) {

        }
        bluetoothServerSocket = tmp;

        this.activity = activity;
    }

    /** Waits for another device to connect, then starts the game */
    @Override
    public void run() {
        BluetoothSocket bluetoothSocket = null;
        while (true) {
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                break;
            }

            if (bluetoothSocket != null) {
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {

                }

                ConnectionManager.getInstance().setSocket(new BluetoothConnectionSocket(bluetoothSocket));
                ConnectionManager.getInstance().setDeviceType(DeviceType.MASTER);
                activity.startGame();

                break;
            }
        }
    }
}
