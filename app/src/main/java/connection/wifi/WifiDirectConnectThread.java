package connection.wifi;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by Zsolt on 2015.05.02..
 *
 * Connects to another device, which run a WifiDirectAcceptThread
 */
public class WifiDirectConnectThread extends Thread {
    private WifiActivity activity;
    private WifiP2pInfo info;

    public WifiDirectConnectThread(WifiActivity activity, WifiP2pInfo info) {
        this.activity = activity;
        this.info = info;
    }

    /** Connects another device via the socket */
    @Override
    public void run() {
        Socket socket = new Socket();
        String host = info.groupOwnerAddress.getHostAddress();

        try {
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, 8888)), 500);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ConnectionManager.getInstance().setSocket(new WifiDirectSocket(socket));
        ConnectionManager.getInstance().setDeviceType(DeviceType.SLAVE);
        activity.startGame();
    }
}
