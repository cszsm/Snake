package zsolt.cseh.snake;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.UUID;

import connection.bluetooth.BluetoothAcceptThread;
import connection.bluetooth.BluetoothConnectThread;

/**
 * The bluetooth menu activity
 * Allows to create a multiplayer game or connect to another via bluetooth
 */
public class BluetoothActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private SimpleArrayMap<String, BluetoothDevice> devices;
    private UUID uuid;

    private BluetoothConnectThread connectThread;
    private BluetoothAcceptThread acceptThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);

        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        ListView listView = (ListView) findViewById(R.id.listDevices);
        devices = new SimpleArrayMap<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(BluetoothActivity.this, "Device does not support Bluetooth.", Toast.LENGTH_LONG).show();
        }

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(BluetoothActivity.this, android.R.layout.simple_list_item_1, arrayList);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    arrayAdapter.add(device.getName());
                    if (device.getUuids() != null) {
                        devices.put(device.getName(), device);
                    }
                }
            }
        };

        listView.setAdapter(arrayAdapter);

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);

        turnOn();

        Button btnStartDiscovery = (Button) findViewById(R.id.btnStartDiscovery);
        btnStartDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.cancelDiscovery();
                arrayList.clear();
                devices.clear();
                arrayAdapter.notifyDataSetChanged();
                bluetoothAdapter.startDiscovery();
                Toast.makeText(BluetoothActivity.this, "Starting discovery...", Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView clickedView = (TextView) view;
                BluetoothDevice clickedDevice = devices.get(clickedView.getText());
                clickedDevice.fetchUuidsWithSdp();
                ParcelUuid[] parcelUuids = clickedDevice.getUuids();

                if (parcelUuids == null) {
                    Toast.makeText(BluetoothActivity.this, "This device is not running the game", Toast.LENGTH_LONG).show();
                } else {
                    connectThread = new BluetoothConnectThread(bluetoothAdapter,
                            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"), clickedDevice, BluetoothActivity.this);
                    connectThread.start();
                }
            }
        });

        Button btnStartServer = (Button) findViewById(R.id.btnStartServer);
        btnStartServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enableDiscoverabilityIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                enableDiscoverabilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(enableDiscoverabilityIntent);

                if (acceptThread != null) {
                    acceptThread.requestStop();
                }

                acceptThread = new BluetoothAcceptThread(bluetoothAdapter, uuid, BluetoothActivity.this);
                acceptThread.start();
                Toast.makeText(BluetoothActivity.this, "Waiting for another device...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void turnOn() {
        final int REQUEST_ENABLE_BT = 1;

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }
    }

    public void startGame() {
        Intent intent;
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tbtBtTest);
        if(toggleButton.isChecked()) {
            intent = new Intent(BluetoothActivity.this, SynchronizerActivity.class);
        } else {
            intent = new Intent(BluetoothActivity.this, MultiplayerActivity.class);
        }
        startActivity(intent);
    }
}
