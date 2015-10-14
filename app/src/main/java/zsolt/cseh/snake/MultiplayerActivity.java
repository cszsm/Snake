package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.TransferThread;
import model.Game;
import view.GameView;

/**
 * A multiplayer game
 */
public class MultiplayerActivity extends Activity {

    private GameView multiplayerView;
//    private TransferThread transferThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionSocket socket = ConnectionManager.getInstance().getSocket();
        TransferThread transferThread = new TransferThread(socket);
        transferThread.start();
        ConnectionManager.getInstance().setTransferThread(transferThread);

        Game.getInstance().reset();
//        Game.getInstance().getGameManager().setTransferThread(transferThread);
        multiplayerView = new GameView(getApplicationContext(), null);

        setContentView(multiplayerView);
    }

    @Override
    protected void onPause() {
        super.onPause();

//        transferThread.cancel();
        ConnectionManager.getInstance().getTransferThread().cancel();

        Intent intent = new Intent(MultiplayerActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        multiplayerView.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(MultiplayerActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
