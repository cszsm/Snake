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
public class WifiThread extends Thread {

    private TransferThread transferThread;
    private WifiManager wifiManager;

    public WifiThread(TransferThread transferThread, WifiManager wifiManager) {
        this.transferThread = transferThread;
        this.wifiManager = wifiManager;
    }

    @Override
    public void run() {
        Log.v("udp", "SENT01");
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        byte[] bytes = BigInteger.valueOf(ipAddress).toByteArray();
        WifiConnectionPacket packet = null;
        try {
            packet = new WifiConnectionPacket(InetAddress.getByAddress(bytes));
            Log.v("udp", "SENT02");
        } catch (UnknownHostException e) {
            Log.v("udp", "SENT02__0");
            e.printStackTrace();
        }
        transferThread.write(packet);
        if (packet != null) {
            Log.v("udp", "SENT03");
        } else {
            Log.v("udp", "SENT04");
        }

//        try {
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            int ipAddress = wifiInfo.getIpAddress();
//            String ip = String.format(Locale.getDefault(), "%d.%d.%d.%d",
//                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
//                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
//            Log.v("udp", "IP - " + ip);
//        } catch (Exception e) {
//            Log.v("udp", "error: address");
//        }

//        try {
//            InetAddress address = InetAddress.getLocalHost();
//            Log.v("udp", "IP - " + address);
//        } catch (UnknownHostException e) {
//            Log.v("udp", "error: address");
//            e.printStackTrace();
//        }
    }
}
