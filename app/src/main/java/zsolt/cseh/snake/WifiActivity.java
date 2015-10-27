package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import connection.SnakePacket;
import connection.TransferThread;
import connection.wifi.WifiSocket;
import model.enumeration.Direction;

public class WifiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

//        try {
//            DatagramSocket socket = new DatagramSocket(8888);
//            socket.setBroadcast(true);
//        } catch (SocketException e) {
//            Log.v("wifi", "datagramsocket... error");
//        }


        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
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

        TransferThread transferThread = new TransferThread(wifiSocket);
        transferThread.start();


        SnakePacket packet = new SnakePacket(Direction.DOWN, 13, 42);
        transferThread.write(packet);

        Button btnSend = (Button) findViewById(R.id.btnWifiSend);
        final WifiSocket finalWifiSocket = wifiSocket;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnakePacket packet = new SnakePacket(Direction.DOWN, 13, 42);
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
        final WifiSocket finalWifiSocket1 = wifiSocket;
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return InetAddress.getByAddress(quads);
    }
}
