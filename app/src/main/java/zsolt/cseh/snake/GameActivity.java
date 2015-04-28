package zsolt.cseh.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

import connection.bluetooth.BluetoothManager;
import connection.enumaration.DeviceType;
import model.Game;
import view.GameControl;


public class GameActivity extends Activity {

    private GameControl gameControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BluetoothManager.getInstance().setDeviceType(DeviceType.NONE);

        Point point = new Point(getIntent().getIntExtra("resolutionX", 480), getIntent().getIntExtra("resolutionY", 320));
        Game.getInstance().reset();
        gameControl = new GameControl(getApplicationContext(), null, point);
        setContentView(gameControl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameControl.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameControl.resume();
    }
}
