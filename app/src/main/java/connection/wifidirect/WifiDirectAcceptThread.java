package connection.wifidirect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiDirectActivity;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Waits for another device to connect
 */
public class WifiDirectAcceptThread extends Thread {

    private final ServerSocket serverSocket;
    private WifiDirectActivity activity;
    private volatile boolean stopSignal;

    public WifiDirectAcceptThread(WifiDirectActivity activity) {
        ServerSocket tmp = null;

        try {
            tmp = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = tmp;

        this.activity = activity;
        ConnectionManager.getInstance().setDeviceType(DeviceType.SERVER);
        stopSignal = false;
    }

    /**
     * Waits for another device to connect, then starts the game
     */
    @Override
    public void run() {
        Socket client;

        while (!stopSignal) {
            try {
                client = serverSocket.accept();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                break;
            }

            if (client != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ConnectionManager.getInstance().setSocket(new WifiDirectSocket(client));
                activity.startGame();
//                activity.startTest();
//                activity.startSynchronizer();
                break;
            }
        }
    }

    public void requestStop() {
        stopSignal = true;
    }
}
