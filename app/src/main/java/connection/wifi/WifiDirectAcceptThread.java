package connection.wifi;

import android.widget.Toast;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Waits for another device to connect
 */
public class WifiDirectAcceptThread extends Thread {

    private final ServerSocket serverSocket;
    private WifiActivity activity;
    private volatile boolean stopSignal;

    public WifiDirectAcceptThread(WifiActivity activity) {
        ServerSocket tmp = null;
        try {
            tmp = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = tmp;
        this.activity = activity;
        stopSignal = false;
    }

    /**
     * Waits for another device to connect, then starts the game
     */
    @Override
    public void run() {
        Socket client = null;
        while (!stopSignal) {
            try {
                client = serverSocket.accept();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }

            if (client != null) {

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ConnectionManager.getInstance().setSocket(new WifiDirectSocket(client));
                ConnectionManager.getInstance().setDeviceType(DeviceType.MASTER);
                activity.startGame();
                break;
            }
        }
    }

    public void requestStop() {
        stopSignal = true;
    }
}
