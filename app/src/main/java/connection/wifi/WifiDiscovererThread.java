package connection.wifi;

import android.support.v4.util.SimpleArrayMap;
import android.widget.ArrayAdapter;

import java.net.InetAddress;
import java.util.ArrayList;

import connection.TransferThread;

/**
 * Created by zscse on 2015. 11. 03..
 */
public class WifiDiscovererThread extends Thread {
    private ArrayList<String> deviceArrayList;
    private ArrayAdapter<String> deviceArrayAdapter;
    private SimpleArrayMap<String, InetAddress> devices;
    private TransferThread transferThread;

    public WifiDiscovererThread(ArrayList<String> deviceArrayList,
                                ArrayAdapter<String> deviceArrayAdapter,
                                SimpleArrayMap<String, InetAddress> devices,
                                TransferThread transferThread) {
        this.deviceArrayList = deviceArrayList;
        this.deviceArrayAdapter = deviceArrayAdapter;
        this.devices = devices;
        this.transferThread = transferThread;
    }

    @Override
    public void run() {
        deviceArrayList.clear();
        devices.clear();

        WifiConnectionPacket connectionPacket;

        while (true) {
            connectionPacket = (WifiConnectionPacket) transferThread.getPacket();
            if (connectionPacket != null) {
//                deviceArrayAdapter.add("igen");
                deviceArrayList.add(connectionPacket.getAddress().toString());
                devices.put(connectionPacket.getAddress().toString(), connectionPacket.getAddress());
            }
        }
    }
}
