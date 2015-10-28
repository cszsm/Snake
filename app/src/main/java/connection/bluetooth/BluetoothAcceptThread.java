package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 * <p/>
 * Waits for another device to connect
 */
public class BluetoothAcceptThread extends Thread {

    private final BluetoothServerSocket bluetoothServerSocket;
    private BluetoothActivity activity;
    private volatile boolean stopSignal;

    public BluetoothAcceptThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothActivity activity) {
        BluetoothServerSocket tmp = null;

        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Snake", uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bluetoothServerSocket = tmp;

        this.activity = activity;
        ConnectionManager.getInstance().setDeviceType(DeviceType.SERVER);
        stopSignal = false;
    }

    /**
     * Waits for another device to connect, then starts the game
     */
    @Override
    public void run() {
        BluetoothSocket bluetoothSocket;

        while (!stopSignal) {
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (bluetoothSocket != null) {
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ConnectionManager.getInstance().setSocket(new BluetoothConnectionSocket(bluetoothSocket));
//                activity.startGame();
                activity.startTest();
//                activity.startSynchronizer();
                break;
            }
        }
    }

    public void requestStop() {
        stopSignal = true;
    }
}
