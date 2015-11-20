package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connection.ConnectionManager;
import connection.TransferThread;
import connection.enumeration.DeviceType;
import connection.wifi.WifiConnectionPacket;
import connection.wifi.WifiDiscovererThread;
import connection.wifi.WifiSocket;
import connection.wifi.WifiSenderThread;

public class WifiActivity extends Activity {

    private ArrayList<String> deviceArrayList;
    private ArrayAdapter<String> deviceArrayAdapter;
    private SimpleArrayMap<String, InetAddress> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        ListView deviceListView = (ListView) findViewById(R.id.listWifiDevices);
        devices = new SimpleArrayMap<>();

        DatagramSocket broadcastSocket = null;
        try {
            broadcastSocket = new DatagramSocket(8888);
            broadcastSocket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        WifiSocket broadcastWifiSocket = null;
        try {
            broadcastWifiSocket = new WifiSocket(broadcastSocket, getBroadcastAddress(), 8888);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        final TransferThread broadcastThread = new TransferThread(broadcastWifiSocket);
        broadcastThread.start();

        deviceArrayList = new ArrayList<>();
        deviceArrayAdapter = new ArrayAdapter<>(WifiActivity.this, android.R.layout.simple_list_item_1, deviceArrayList);
        deviceListView.setAdapter(deviceArrayAdapter);

        final WifiDiscovererThread connectionThread = new WifiDiscovererThread(deviceArrayList, deviceArrayAdapter, devices, broadcastThread, WifiActivity.this);
        connectionThread.start();

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedView = (TextView) view;
                InetAddress deviceAddress = devices.get(clickedView.getText());
                Toast.makeText(WifiActivity.this, deviceAddress.toString(), Toast.LENGTH_SHORT).show();

                WifiSenderThread wifiSenderThread = new WifiSenderThread(broadcastThread, (WifiManager) getSystemService(WIFI_SERVICE), deviceAddress);
                wifiSenderThread.start();

                try {
                    wifiSenderThread.join();

                    DatagramSocket datagramSocket = null;
                    try {
                        datagramSocket = new DatagramSocket(8889);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                    WifiSocket wifiSocket = new WifiSocket(datagramSocket, deviceAddress, 8889);
                    Log.v("udp", "destination address: " + deviceAddress);

                    connectionThread.cancel();
                    broadcastThread.cancel();
                    ConnectionManager.getInstance().setDeviceType(DeviceType.SERVER);
                    ConnectionManager.getInstance().setSocket(wifiSocket);
                    startGame();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnSend = (Button) findViewById(R.id.btnWifiSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiSenderThread wifiSenderThread = new WifiSenderThread(broadcastThread, (WifiManager) getSystemService(WIFI_SERVICE));
                wifiSenderThread.start();
            }
        });

        Button btnRefresh = (Button) findViewById(R.id.btnWifiRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public InetAddress getBroadcastAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if(dhcpInfo == null) {
            Log.v("udp", "dhcpInfo is null");
            return null;
        }

        int broadcast = (dhcpInfo.ipAddress & dhcpInfo.netmask) | ~dhcpInfo.netmask;
        byte[] quads = new byte[4];
        for (int i = 0; i < 4; i++) {
            quads[i] = (byte) ((broadcast >> i * 8) & 0xFF);
        }

        InetAddress address = InetAddress.getByAddress(quads);
        return address;
    }

    public void startGame() {
        Intent intent = new Intent(WifiActivity.this, MultiplayerActivity.class);
//        Intent intent = new Intent(WifiActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
