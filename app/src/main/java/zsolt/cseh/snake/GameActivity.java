package zsolt.cseh.snake;

import android.app.Activity;
import android.os.Bundle;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import model.Game;
import view.GameView;

/**
 * A single player game
 */
public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionManager.getInstance().setDeviceType(DeviceType.NONE);

        Game.getInstance().reset();
        gameView = new GameView(getApplicationContext(), null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.stop();
    }
}
