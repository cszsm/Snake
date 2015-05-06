package zsolt.cseh.snake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.Packet;
import connection.TransferThread;
import model.enumeration.Direction;
import test.TestThread;


public class WifiTestActivity extends Activity {

    private TransferThread transferThread;
    private Packet packet;
    private TestThread testThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_test);

        ConnectionSocket socket = ConnectionManager.getInstance().getSocket();
        transferThread = new TransferThread(socket);
        transferThread.start();

        packet = new Packet(Direction.UP, 10, 10);
        testThread = new TestThread(transferThread);

        Button btnTest = (Button) findViewById(R.id.btnWifiTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testThread.start();
//                try {
//                    transferThread.write(PacketSerialization.serialize(packet));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
}
