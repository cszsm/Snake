package zsolt.cseh.snake;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import connection.bluetooth.BluetoothManager;
import connection.Packet;
import connection.PacketSerialization;
import connection.bluetooth.TransferThread;
import model.enumeration.Direction;
import test.TestThread;


public class BluetoothTestActivity extends Activity {

    private TransferThread transferThread;
    private Packet packet;
//    private TestThread testThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        BluetoothSocket bluetoothSocket = BluetoothManager.getInstance().getBluetoothSocket();
        transferThread = new TransferThread(bluetoothSocket);
        transferThread.start();

        packet = new Packet(Direction.UP, 10, 10);
//        testThread = new TestThread(transferThread);

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                testThread.start();
                try {
                    transferThread.write(PacketSerialization.serialize(packet));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
