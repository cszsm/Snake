package zsolt.cseh.snake;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import connection.wifi.WifiDirectBroadcastReceiver;


public class WifiActivity extends Activity {

    private IntentFilter intentFilter;
    private WifiP2pManager manager;
    private Channel channel;
    private BroadcastReceiver receiver;
    private WifiP2pManager.PeerListListener peerListListener;
    private List peerList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WifiDirectBroadcastReceiver(manager, channel, this, peerListListener);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        listView = (ListView) findViewById(R.id.listWifiDevices);

        peerList = new ArrayList();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WifiActivity.this,
                android.R.layout.simple_list_item_1, peerList);
        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {

                Toast.makeText(WifiActivity.this, "onPeersAvailable", Toast.LENGTH_LONG).show();
                Log.v("wifi", "OnPeersAvailable");
                peerList.clear();
                peerList.addAll(peers.getDeviceList());
                arrayAdapter.notifyDataSetChanged();
                if (peerList.size() == 0) {
                    Toast.makeText(WifiActivity.this, "no peers", Toast.LENGTH_LONG).show();
                    Log.v("wifi", "NoPeers");
                } else {
                    Toast.makeText(WifiActivity.this, peerList.size(), Toast.LENGTH_LONG).show();
                    Log.v("wifi", "Peers");
                }
            }
        };

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
}
