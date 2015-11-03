package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

import connection.TransferThread;
import connection.wifi.WifiConnectionThread;
import connection.wifi.WifiSocket;
import connection.wifi.WifiThread;

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

        deviceArrayList = new ArrayList<>();
        deviceArrayAdapter = new ArrayAdapter<>(WifiActivity.this, android.R.layout.simple_list_item_1, deviceArrayList);
        deviceListView.setAdapter(deviceArrayAdapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedView = (TextView) view;
                InetAddress deviceAddress = devices.get(clickedView.getText());
                Toast.makeText(WifiActivity.this, deviceAddress.toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        try {
//            DatagramSocket socket = new DatagramSocket(8888);
//            socket.setBroadcast(true);
//        } catch (SocketException e) {
//            Log.v("wifi", "datagramsocket... error");
//        }


        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(8888);
            datagramSocket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        WifiSocket wifiSocket = null;
        try {
            wifiSocket = new WifiSocket(datagramSocket, getBroadcastAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        final TransferThread transferThread = new TransferThread(wifiSocket);
        transferThread.start();

        Button btnSend = (Button) findViewById(R.id.btnWifiSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiThread wifiThread = new WifiThread(transferThread, (WifiManager) getSystemService(WIFI_SERVICE));
                wifiThread.start();



//                SnakePacket packet = new SnakePacket(Direction.DOWN, 13, 42);
//                transferThread.write(packet);


//                if (finalWifiSocket != null) {
//                    try {
//                        finalWifiSocket.send(bytes);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }




//                try {
//                    DatagramSocket socket = new DatagramSocket();
//                    socket.setBroadcast(true);
////                    InetAddress local = InetAddress.getByName("10.80.1.95");
//                    byte[] bytes = message.getBytes();
//                    DatagramPacket packet = new DatagramPacket(bytes, message.length(), getBroadcastAddress(), 8888);
//                    socket.send(packet);
//                } catch (SocketException e) {
//                    Log.v("wifi", "datagramsocket error");
//                } catch (UnknownHostException e) {
//                    Log.v("wifi", "inetadress error");
//                } catch (IOException e) {
//                    Log.v("wifi", "send error");
//                }
            }
        });

        Button btnReceive = (Button) findViewById(R.id.btnWifiReceive);
//        final WifiSocket finalWifiSocket1 = wifiSocket;
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WifiConnectionThread connectionThread = new WifiConnectionThread(deviceArrayList, deviceArrayAdapter, devices, transferThread);
                connectionThread.start();

//                SnakePacket packet = (SnakePacket) transferThread.getPacket();

//                byte[] packet;
//                packet = new byte[1024];
//                int bytes = transferThread.getPacket(packet);
//                DatagramPacket packet = new DatagramPacket(transferThread.getPacket(), )
//                Log.v("udp", "GOT - " + packet.getDirection());


//                String message;
//                byte[] bytes = new byte[1024];
//
//                int length = 0;
//                try {
//                    length = finalWifiSocket1.receive(bytes);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                message = new String(bytes, 0, length);
//                Log.v("udp", message);




//                byte[] bytes = new byte[1024];
//                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
//                try {
//                    DatagramSocket socket = new DatagramSocket(8888);
//                    socket.receive(packet);
//                    message = new String(bytes, 0, packet.getLength());
//                    Log.v("wifi", "OK: " + message);
//                    socket.close();
//                } catch (SocketException e) {
//                    Log.v("wifi", "datagramsocket error");
//                } catch (IOException e) {
//                    Log.v("wifi", "receive exception");
//                }
            }
        });
    }

    Button btnRefresh = (Button) findViewById(R.id.btnWifiRefresh);

    private InetAddress getBroadcastAddress() throws UnknownHostException {
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

        Log.v("udp", String.valueOf(quads));
        return InetAddress.getByAddress(quads);
    }
}
