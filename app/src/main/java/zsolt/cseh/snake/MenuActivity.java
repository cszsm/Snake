package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import model.Game;
import view.ScreenResolution;

/**
 * The main menu activity
 * Contains buttons which lead to other activities
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ScreenResolution.getInstance().setX(point.x);
        ScreenResolution.getInstance().setY(point.y);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.getInstance();
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        btnStart.setEnabled(false);

        Button btnBluetooth = (Button) findViewById(R.id.btnBluetooth);
        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });

        Button btnWifi = (Button) findViewById(R.id.btnWifi);
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WifiActivity.class);
                startActivity(intent);
            }
        });
    }
}
