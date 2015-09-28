package zsolt.cseh.snake;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long millis = new GregorianCalendar().getTimeInMillis();
                String ms = String.valueOf(millis % 1000);
                millis /= 1000;
                String s = String.valueOf(millis % 60);
                millis /= 60;
                String min = String.valueOf(millis % 60);
                millis /= 60;
                String h = String.valueOf(millis % 24);

                String time = h + ":" + min + ":" + s + "." + ms;

                Toast.makeText(MenuActivity.this, time, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
