package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import model.Position;
import model.Snake;
import model.enumeration.Player;

/**
 * Created by Zsolt on 2015.03.07..
 * <p/>
 * Draws the snake
 */
public class SnakeView extends RectangleView {

    private Snake snake;

    private Paint paintHead;
    private Paint paintBody;

    /**
     * Sets the colors of the food
     */
    public SnakeView(int size, Snake snake, Player player) {
        fieldSize = size;
        this.snake = snake;

        paintHead = new Paint();
        paintHead.setStyle(Paint.Style.FILL);
        paintHead.setColor(Color.argb(127, 0, 0, 0));

        paintBody = new Paint();
        paintBody.setStyle(Paint.Style.FILL);
        if (player == Player.ONE) {
            paintBody.setColor(Color.argb(255, 0, 128, 0));
        } else {
            paintBody.setColor(Color.argb(255, 128, 0, 0));
        }
    }

    /**
     * Draws the snake
     */
    public void draw(Canvas canvas) {
        for (Position position : snake.getBody()) {
            drawRectangle(position.x, position.y, canvas, paintBody);
            drawRectangle(position.x, position.y, canvas, paintBorder);
        }

        Position head = snake.getBody().get(0);
        drawRectangle(head.x, head.y, canvas, paintHead);
        drawRectangle(head.x, head.y, canvas, paintBorder);
    }
}
