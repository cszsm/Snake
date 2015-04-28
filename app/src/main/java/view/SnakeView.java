package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import model.Snake;

/**
 * Created by Zsolt on 2015.03.07..
 */
public class SnakeView extends RectangleView {
    private Snake snake;

    private Paint paintHead;
    private Paint paintBody;

    public SnakeView(int size, Snake snake) {
        fieldSize = size;
        this.snake = snake;

        paintHead = new Paint();
        paintHead.setStyle(Paint.Style.FILL);
        paintHead.setColor(Color.argb(127, 0, 0, 0));

        paintBody = new Paint();
        paintBody.setStyle(Paint.Style.FILL);
        paintBody.setColor(Color.argb(255, 0, 128, 0));

        paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        for(Point point : snake.getBody()) {
            drawRectangle(point.x, point.y, canvas, paintBody);
            drawRectangle(point.x, point.y, canvas, paintBorder);
        }
        Point head = snake.getBody().get(0);
        drawRectangle(head.x, head.y, canvas, paintHead);
        drawRectangle(head.x, head.y, canvas, paintBorder);
    }
}
