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

        Button btnSend = (Button) findViewById(R.id.btnWifiSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Teszt";
                try {
                    DatagramSocket socket = new DatagramSocket();
                    InetAddress local = InetAddress.getByName("10.80.1.95");
                    byte[] bytes = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(bytes, message.length(), local, 8888);
                    socket.send(packet);
                } catch (SocketException e) {
                    Log.v("wifi", "datagramsocket error");
                } catch (UnknownHostException e) {
                    Log.v("wifi", "inetadress error");
                } catch (IOException e) {
                    Log.v("wifi", "send error");
                }
            }
        });

        Button btnReceive = (Button) findViewById(R.id.btnWifiReceive);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                try {
                    DatagramSocket socket = new DatagramSocket(8888);
                    socket.receive(packet);
                    message = new String(bytes, 0, packet.getLength());
                    Log.v("wifi", "OK: " + message);
                    socket.close();
                } catch (SocketException e) {
                    Log.v("wifi", "datagramsocket error");
                } catch (IOException e) {
                    Log.v("wifi", "receive exception");
                }
            }
        });
    }

    private InetAddress getBroadcastAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if(dhcpInfo == null) {
            Log.v("wifi", "dhcpInfo is null");
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
