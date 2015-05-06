package zsolt.cseh.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.TransferThread;
import model.Game;
import view.GameView;
import view.ScreenResolution;

/**
 * A multiplayer game
 */
public class MultiplayerActivity extends Activity {

    private GameView multiplayerView;
    private TransferThread transferThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectionSocket socket = ConnectionManager.getInstance().getSocket();
        transferThread = new TransferThread(socket);
        transferThread.start();

        Game.getInstance().reset();
        Game.getInstance().getGameManager().setTransferThread(transferThread);
        multiplayerView = new GameView(getApplicationContext(), null);

        setContentView(multiplayerView);
    }
}
