package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import connection.ConnectionManager;
import connection.enumaration.DeviceType;
import connection.wifi.TransferThread;
import connection.wifi.WifiDirectManager;
import model.CollisionDetector;
import model.Game;

/**
 * Created by Zsolt on 2015.04.02..
 */
public class MultiplayerView extends View {
    private BoardView boardView;
    private SnakeView snakeView;
    private FoodView foodView;

    private TouchControl touch;
    private CollisionDetector collisionControl;

    private MultiplayerTimingThread timingThread;
    private TransferThread transferThread;

    private Paint textPaint;

    private boolean gameOver;

    public MultiplayerView(Context context, AttributeSet attrs, Point resolution, TransferThread transferThread) {
        super(context, attrs);

        int blockSize = resolution.y / 9;

        this.transferThread = transferThread;

        boardView = new BoardView(blockSize, Game.getInstance().getBoard());
        snakeView = new SnakeView(blockSize, Game.getInstance().getSnake());
        foodView = new FoodView(blockSize, Game.getInstance().getFoodManager());

        touch = new TouchControl();

        collisionControl = new CollisionDetector(Game.getInstance().getBoard(), Game.getInstance().getSnake());

        timingThread = new MultiplayerTimingThread(this);
        timingThread.start();

        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(144);

        gameOver = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boardView.draw(canvas);
        snakeView.draw(canvas);
        foodView.draw(canvas);

        if (collisionControl.doesCollide()) {
            gameOver = true;
            pause();
        }
        if (gameOver)
            canvas.drawText("Game Over", 100, 220, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ConnectionManager.getInstance().getDeviceType() == DeviceType.MASTER)
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touch.setLastDown(event);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                touch.setLastUp(event);
                touch.setDirection(Game.getInstance().getSnakeManager());
            }
        return true;
    }

    public void pause() {
        timingThread.setPauseSignal(true);
    }

    public void resume() {
        timingThread.setPauseSignal(false);
    }
}
