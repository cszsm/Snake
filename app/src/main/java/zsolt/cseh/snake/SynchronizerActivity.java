package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import ptp.MasterSynchronizerThread;
import ptp.SlaveSynchronizerThread;

public class SynchronizerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronizer);

        Button bntSync = (Button) findViewById(R.id.btnSync);
        bntSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionManager.getInstance().getDeviceType() == DeviceType.SERVER) {
                    MasterSynchronizerThread synchronizerThread = new MasterSynchronizerThread();
                    synchronizerThread.start();
                } else {
                    SlaveSynchronizerThread synchronizerThread = new SlaveSynchronizerThread();
                    synchronizerThread.start();
                }
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
