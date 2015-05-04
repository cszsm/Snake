package zsolt.cseh.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import model.Game;
import view.GameView;


public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionManager.getInstance().setDeviceType(DeviceType.NONE);

        Point point = new Point(getIntent().getIntExtra("resolutionX", 480), getIntent().getIntExtra("resolutionY", 320));
        Game.getInstance().reset();
        gameView = new GameView(getApplicationContext(), null, point);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
