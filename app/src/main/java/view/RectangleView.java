package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Zsolt on 2015.03.07..
 *
 * An abstract class for drawing rectangle
 */
abstract class RectangleView {
    protected int fieldSize;
    protected Paint paintBorder;

    public RectangleView() {
        paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
    }

    /** Draws a rectangle with the given parameters */
    protected void drawRectangle(int column, int row, Canvas canvas, Paint paint) {
        canvas.drawRect(column * fieldSize, row * fieldSize, (column + 1) * fieldSize, (row + 1) * fieldSize, paint);
    }
}
