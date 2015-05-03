package connection.wifi;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import connection.enumaration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by Zsolt on 2015.05.02..
 */
public class ConnectThread extends Thread {
    private WifiActivity activity;
    private WifiP2pInfo info;

    public ConnectThread(WifiActivity activity, WifiP2pInfo info) {
        this.activity = activity;
        this.info = info;
    }

    @Override
    public void run() {
        Context context = activity.getApplicationContext();
        Socket socket = new Socket();
        String host = info.groupOwnerAddress.getHostAddress();

        try {
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, 8888)), 500);
        } catch (IOException e) {
            Log.v("wifi", e.getMessage());
            return;
        }

        WifiDirectManager.getInstance().setSocket(socket);
        WifiDirectManager.getInstance().setDeviceType(DeviceType.SLAVE);
        activity.startGame();
    }
}
