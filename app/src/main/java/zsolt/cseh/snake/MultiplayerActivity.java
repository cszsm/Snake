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
        Point point = new Point(ScreenResolution.getInstance().getX(), ScreenResolution.getInstance().getY());
        multiplayerView = new GameView(getApplicationContext(), null, point);

        setContentView(multiplayerView);
    }
}
