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

import java.util.ArrayList;
import java.util.UUID;

import connection.bluetooth.AcceptThread;
import connection.bluetooth.ConnectThread;


public class BluetoothActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private SimpleArrayMap<String, BluetoothDevice> devices;
    private BroadcastReceiver broadcastReceiver;
    private ListView listView;
    private UUID uuid;

    private ConnectThread connectThread;
    private AcceptThread acceptThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);

//        uuid = UUID.randomUUID();
        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        listView = (ListView) findViewById(R.id.listDevices);
        devices = new SimpleArrayMap<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            Toast.makeText(BluetoothActivity.this, "Device does not support Bluetooth.", Toast.LENGTH_LONG).show();

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(BluetoothActivity.this, android.R.layout.simple_list_item_1, arrayList);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    arrayAdapter.add(device.getName());
                    devices.put(device.getName(), device);
                    if (device.getUuids() != null)
                        Toast.makeText(BluetoothActivity.this, device.getName() + " - " + device.getUuids()[0].getUuid().toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        listView.setAdapter(arrayAdapter);

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);

        turnOn();


//        Button btnShow = (Button) findViewById(R.id.btnShow);
//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPairedDevices();
//            }
//        });

        Button btnStart = (Button) findViewById(R.id.btnStartDiscovery);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                devices.clear();
                arrayAdapter.notifyDataSetChanged();
                bluetoothAdapter.startDiscovery();
            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStopDiscovery);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.cancelDiscovery();
            }
        });

//        Button btnEnable = (Button) findViewById(R.id.btnEnableDiscoverability);
//        btnEnable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enableDiscoverability();
//            }
//        });

//        Button btnUUIDS = (Button) findViewById(R.id.btnUuid);
//        btnUUIDS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(uuid != null)
//                    Toast.makeText(BluetoothActivity.this, uuid.toString(), Toast.LENGTH_LONG).show();
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView clickedView = (TextView) view;
//                Toast.makeText(BluetoothActivity.this, devices.get(clickedView.getText()).getAddress(), Toast.LENGTH_LONG).show();
                BluetoothDevice clickedDevice = devices.get(clickedView.getText());
                clickedDevice.fetchUuidsWithSdp();
                ParcelUuid[] parcelUuids = clickedDevice.getUuids();
//                if(parcelUuids != null)
//                    Toast.makeText(BluetoothActivity.this, parcelUuids[0].getUuid().toString(), Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(BluetoothActivity.this, clickedDevice.getName(), Toast.LENGTH_LONG).show();
                if (parcelUuids == null)
                    Toast.makeText(BluetoothActivity.this, "null", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(BluetoothActivity.this, "00001101-0000-1000-8000-00805f9b34fb", Toast.LENGTH_LONG).show();
                    connectThread = new ConnectThread(bluetoothAdapter, UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"), clickedDevice, BluetoothActivity.this);
                    connectThread.start();
                    Toast.makeText(BluetoothActivity.this, "connect - BA", Toast.LENGTH_LONG).show();
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

                acceptThread = new AcceptThread(bluetoothAdapter, uuid, BluetoothActivity.this);
                acceptThread.start();
                Toast.makeText(BluetoothActivity.this, "accept - BA", Toast.LENGTH_LONG).show();
            }
        });

//        Button btnSend = (Button) findViewById(R.id.btnSend);
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                connectThread.send();
//            }
//        });
    }

    private void turnOn() {
        final int REQUEST_ENABLE_BT = 1;

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        } else
            Toast.makeText(BluetoothActivity.this, "Bluetooth is already enabled.", Toast.LENGTH_LONG).show();
    }

//    private void getPairedDevices() {
//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//
//        if (pairedDevices.size() > 0)
//            for (BluetoothDevice bluetoothDevice : pairedDevices)
//                Toast.makeText(BluetoothActivity.this, bluetoothDevice.getName(), Toast.LENGTH_LONG).show();
//    }
//
//    private void enableDiscoverability() {
//        Intent enableDiscoverabilityIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        enableDiscoverabilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(enableDiscoverabilityIntent);
//    }

    public void startGame() {
        Intent intent = new Intent(BluetoothActivity.this, WifiMultiplayerActivity.class);
        startActivity(intent);
    }

//    public void startGame() {
//        Intent intent = new Intent(BluetoothActivity.this, BluetoothTestActivity.class);
//        startActivity(intent);
//    }
}
