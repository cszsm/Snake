package zsolt.cseh.snake;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.graphics.Point;
import android.os.Bundle;

import connection.bluetooth.BluetoothManager;
import connection.bluetooth.TransferThread;
import model.Game;
import view.MultiplayerView;
import view.ScreenResolution;


public class MultiplayerActivity extends Activity {

    private MultiplayerView multiplayerView;
    private TransferThread transferThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BluetoothSocket bluetoothSocket = BluetoothManager.getInstance().getBluetoothSocket();
        transferThread = new TransferThread(bluetoothSocket);
        transferThread.start();

        Game.getInstance().reset();
        Game.getInstance().getGameManager().setTransferThread(transferThread);
        Point point = new Point(ScreenResolution.getInstance().getX(), ScreenResolution.getInstance().getY());
        multiplayerView = new MultiplayerView(getApplicationContext(), null, point, transferThread);

        setContentView(multiplayerView);

//        BluetoothSocket bluetoothSocket = BluetoothManager.getInstance().getBluetoothSocket();
//        transferThread = new TransferThread(bluetoothSocket);
//        Log.v("tag", "CT - transfer thread created");
//        transferThread.start();
//        Log.v("tag", "CT - transfer thread started");
//
//        Button button = (Button) findViewById(R.id.btnSend2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.v("tag", "kuldes");
//                Packet packet = new Packet("kornyek kuldve");
//                try {
//                    transferThread.write(PacketSerialization.serialize(packet));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }
}
