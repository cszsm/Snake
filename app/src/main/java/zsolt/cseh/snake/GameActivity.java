package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
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
        ConnectionManager.getInstance().reset();
        gameView.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
