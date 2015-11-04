package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.TransferThread;
import connection.enumeration.DeviceType;
import ptp.MasterSynchronizerThread;
import ptp.SlaveSynchronizerThread;

public class SynchronizerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronizer);

        ConnectionSocket socket = ConnectionManager.getInstance().getSocket();
        TransferThread transferThread = new TransferThread(socket);
        transferThread.start();
        ConnectionManager.getInstance().setTransferThread(transferThread);

        final Thread synchronizerThread;
        if (ConnectionManager.getInstance().getDeviceType() == DeviceType.SERVER) {
            synchronizerThread = new MasterSynchronizerThread();
        } else {
            synchronizerThread = new SlaveSynchronizerThread();
        }

        Button bntSync = (Button) findViewById(R.id.btnSync);
        bntSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronizerThread.start();
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SynchronizerActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }
}
