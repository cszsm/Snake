package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import connection.ConnectionProperties;
import connection.ConnectionSocket;
import connection.TransferThread;
import model.Game;
import view.GameView;

/**
 * A multiplayer game
 */
public class MultiplayerActivity extends Activity {

    private GameView multiplayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionSocket socket = ConnectionProperties.getInstance().getSocket();
        TransferThread transferThread = new TransferThread(socket);

        transferThread.start();
        ConnectionProperties.getInstance().setTransferThread(transferThread);

        Game.getInstance().reset();
        multiplayerView = new GameView(getApplicationContext(), null);

        setContentView(multiplayerView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = new Intent(MultiplayerActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        multiplayerView.stop();
        ConnectionProperties.getInstance().getTransferThread().cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(MultiplayerActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
