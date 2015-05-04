package zsolt.cseh.snake;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
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

import java.util.ArrayList;
import java.util.List;

import connection.wifi.AcceptThread;
import connection.wifi.ConnectThread;
import connection.wifi.WifiDirectBroadcastReceiver;


public class WifiActivity extends Activity implements ConnectionInfoListener {

    private IntentFilter intentFilter;
    private WifiP2pManager manager;
    private Channel channel;
    private BroadcastReceiver receiver;
    private WifiP2pManager.PeerListListener peerListListener;
    private List peerList;
    private ListView listView;
    private SimpleArrayMap devices;
    private WifiP2pInfo info;

    private AcceptThread acceptThread;
    private ConnectThread connectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        listView = (ListView) findViewById(R.id.listWifiDevices);
        devices = new SimpleArrayMap<>();

        peerList = new ArrayList();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WifiActivity.this,
                android.R.layout.simple_list_item_1, peerList);
        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {

                peerList.clear();
                devices.clear();

                for (WifiP2pDevice device : peers.getDeviceList()) {
                    Log.v("wifi", device.deviceName);
                    devices.put(device.deviceAddress, device);
                    peerList.add(device.deviceAddress);
                }

                arrayAdapter.notifyDataSetChanged();
            }
        };

        receiver = new WifiDirectBroadcastReceiver(manager, channel, this, peerListListener);

        listView.setAdapter(arrayAdapter);

        Button btnStartDiscovery = (Button) findViewById(R.id.btnWifiStartDiscovery);
        btnStartDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(WifiActivity.this, "Discover success", Toast.LENGTH_SHORT).show();
                        Log.v("wifi", "Discover SUCCESS");
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiActivity.this, "Discover failure" + String.valueOf(reason), Toast.LENGTH_SHORT).show();
                        Log.v("wifi", "Discover FAILURE");
                    }
                });
            }
        });

        Button btnStartServer = (Button) findViewById(R.id.btnWifiStartServer);
        btnStartServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptThread = new AcceptThread(WifiActivity.this);
                acceptThread.start();
                Toast.makeText(WifiActivity.this, "accept - wifiactivity", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = (Button) findViewById(R.id.btnWifiConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectThread = new ConnectThread(WifiActivity.this, info);
                connectThread.start();
                Toast.makeText(WifiActivity.this, "connect - wifiactivity", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView clickedView = (TextView) view;

                WifiP2pDevice clickedDevice = (WifiP2pDevice) devices.get(clickedView.getText());
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = clickedDevice.deviceAddress;
                //config.wps.setup = WpsInfo.PBC;

                manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(WifiActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    public void startGame() {
        Intent intent = new Intent(WifiActivity.this, MultiplayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        this.info = info;
        String groupOwnerAddress = info.groupOwnerAddress.getHostAddress();
        Log.v("wifi", groupOwnerAddress);
    }
}
