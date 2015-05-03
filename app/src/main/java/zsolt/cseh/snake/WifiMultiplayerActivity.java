package zsolt.cseh.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

import connection.ConnectionManager;
import connection.ConnectionSocket;
import connection.wifi.TransferThread;
import model.Game;
import view.MultiplayerView;
import view.ScreenResolution;


public class WifiMultiplayerActivity extends Activity {

    private MultiplayerView multiplayerView;
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
        multiplayerView = new MultiplayerView(getApplicationContext(), null, point, transferThread);

        setContentView(multiplayerView);
    }
}
