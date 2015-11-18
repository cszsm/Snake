package connection.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connection.ConnectionManager;
import connection.TransferThread;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by zscse on 2015. 11. 03..
 */
public class WifiDiscovererThread extends Thread {
    private ArrayList<String> deviceArrayList;
    private ArrayAdapter<String> deviceArrayAdapter;
    private SimpleArrayMap<String, InetAddress> devices;
    private TransferThread transferThread;
    private WifiActivity activity;

    public WifiDiscovererThread(ArrayList<String> deviceArrayList,
                                ArrayAdapter<String> deviceArrayAdapter,
                                SimpleArrayMap<String, InetAddress> devices,
                                TransferThread transferThread,
                                WifiActivity activity) {
        this.deviceArrayList = deviceArrayList;
        this.deviceArrayAdapter = deviceArrayAdapter;
        this.devices = devices;
        this.transferThread = transferThread;
        this.activity = activity;
    }

    @Override
    public void run() {
        deviceArrayList.clear();
        devices.clear();

        WifiConnectionPacket connectionPacket;

        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        InetAddress address = null;
        try {
            address = InetAddress.getByAddress(BigInteger.valueOf(ipAddress).toByteArray());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Log.v("udp", "discovering started");
        while (true) {
            connectionPacket = (WifiConnectionPacket) transferThread.getPacket();
            if (connectionPacket != null) {
                try {
                    Log.v("udp", "source" + connectionPacket.getSource().toString());
                    Log.v("udp", "destination" + connectionPacket.getDestination().toString());
                    Log.v("udp", "address" + address.toString());
                } catch (Exception e) {
                    Log.v("udp", "log error");
                    e.printStackTrace();
                }
//                deviceArrayAdapter.add("igen");
                if(connectionPacket.getDestination() == address) {

                    Log.v("udp", "dst - " + connectionPacket.getDestination().toString());

                    DatagramSocket datagramSocket = null;
                    try {
                        datagramSocket = new DatagramSocket(8889);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                    WifiSocket wifiSocket = new WifiSocket(datagramSocket, connectionPacket.getDestination());

                    ConnectionManager.getInstance().setSocket(wifiSocket);
                    activity.startGame();


                } else {
                    Log.v("udp", "tolist");
                    deviceArrayList.add(connectionPacket.getSource().toString());
                    devices.put(connectionPacket.getSource().toString(), connectionPacket.getSource());
                }
            }
        }
    }
}
