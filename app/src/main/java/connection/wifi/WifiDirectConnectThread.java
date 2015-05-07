package connection.wifi;

import android.net.wifi.p2p.WifiP2pInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by Zsolt on 2015.05.02..
 * <p/>
 * Connects to another device, which run a WifiDirectAcceptThread
 */
public class WifiDirectConnectThread extends Thread {
    private WifiActivity activity;
    private WifiP2pInfo info;

    public WifiDirectConnectThread(WifiActivity activity, WifiP2pInfo info) {
        this.activity = activity;
        this.info = info;
    }

    /**
     * Connects another device via the socket
     */
    @Override
    public void run() {
            Socket socket = new Socket();
            String host;
        try {
            host = info.groupOwnerAddress.getHostAddress();
        } catch (NullPointerException e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "An error occurred, please try connect again", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }

        try {
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, 8888)), 1000);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ConnectionManager.getInstance().setSocket(new WifiDirectSocket(socket));
        ConnectionManager.getInstance().setDeviceType(DeviceType.SLAVE);
        activity.startGame();
    }
}