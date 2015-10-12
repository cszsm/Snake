package zsolt.cseh.snake;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class WifiActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
