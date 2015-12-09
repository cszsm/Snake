package connection.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connection.ConnectionProperties;
import connection.TransferThread;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.WifiActivity;

/**
 * Created by zscse on 2015. 11. 03..
 */
public class WifiDiscovererThread extends Thread {
    private ArrayList<String> deviceArrayList;
    private SimpleArrayMap<String, InetAddress> devices;
    private TransferThread transferThread;
    private WifiActivity activity;
    private boolean stopSignal;

    public WifiDiscovererThread(ArrayList<String> deviceArrayList,
                                SimpleArrayMap<String, InetAddress> devices,
                                TransferThread transferThread,
                                WifiActivity activity) {
        this.deviceArrayList = deviceArrayList;
        this.devices = devices;
        this.transferThread = transferThread;
        this.activity = activity;
        stopSignal = false;
    }

    @Override
    public void run() {
        deviceArrayList.clear();
        devices.clear();

        WifiConnectionPacket connectionPacket;

        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));

        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Log.v("udp", "discovering started");
        while (!stopSignal) {
            connectionPacket = (WifiConnectionPacket) transferThread.getPacket();
            if (connectionPacket != null) {
                InetAddress destination = connectionPacket.getDestination();
//                deviceArrayAdapter.add("igen");
                // If the destination address in the received packet is the device's own address...
                if (destination != null && destination.toString().equals(address.toString())) {

                    Log.v("udp", "dst - " + connectionPacket.getDestination().toString());

                    DatagramSocket datagramSocket = null;
                    try {
                        datagramSocket = new DatagramSocket(8889);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                    WifiSocket wifiSocket = new WifiSocket(datagramSocket, connectionPacket.getSource(), 8889);
                    Log.v("udp", "own address: " + address);
                    Log.v("udp", "destination address: " + connectionPacket.getSource());

                    stopSignal = true;
                    transferThread.cancel();
                    ConnectionProperties.getInstance().setDeviceType(DeviceType.CLIENT);
                    ConnectionProperties.getInstance().setSocket(wifiSocket);
                    activity.startGame();


                } else {
                    Log.v("udp", "tolist");
                    deviceArrayList.add(connectionPacket.getSource().toString());
                    devices.put(connectionPacket.getSource().toString(), connectionPacket.getSource());
                }
            }
        }
    }

    public void cancel() {
        stopSignal = true;
    }
}
