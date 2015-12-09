package connection.wifi;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import connection.SnakePacket;
import connection.TransferThread;
import model.enumeration.Direction;

/**
 * Created by zscse on 2015. 10. 31..
 */
public class WifiSenderThread extends Thread {

    private TransferThread transferThread;
    private WifiManager wifiManager;
    private InetAddress destination;

    public WifiSenderThread(TransferThread transferThread, WifiManager wifiManager) {
        this.transferThread = transferThread;
        this.wifiManager = wifiManager;
    }

    public WifiSenderThread(TransferThread transferThread, WifiManager wifiManager, InetAddress destination) {
        this.transferThread = transferThread;
        this.wifiManager = wifiManager;
        this.destination = destination;
    }

    @Override
    public void run() {
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        WifiConnectionPacket packet = null;
        try {
            if(destination != null) {
                packet = new WifiConnectionPacket(InetAddress.getByName(ip), destination);
            } else {
                packet = new WifiConnectionPacket(InetAddress.getByName(ip));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        transferThread.write(packet);
    }
}
