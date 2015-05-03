package connection.wifi;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by Zsolt on 2015.05.02..
 */
public class AcceptThread extends Thread {

    private final ServerSocket serverSocket;
    private WifiActivity activity;

    public AcceptThread(WifiActivity activity) {
        ServerSocket tmp = null;
        try {
            tmp = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = tmp;
        this.activity = activity;
    }

    @Override
    public void run() {
        Socket client = null;
        while(true) {
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (client != null) {
                Log.v("wifi", "Server started");
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
}
