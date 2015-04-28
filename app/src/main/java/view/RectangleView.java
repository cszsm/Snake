package view;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Zsolt on 2015.03.07..
 */
abstract class RectangleView {
    protected int fieldSize;
    protected Paint paintBorder;

    protected void drawRectangle(int column, int row, Canvas canvas, Paint paint) {
        canvas.drawRect(column * fieldSize, row * fieldSize, (column + 1) * fieldSize, (row + 1) * fieldSize, paint);
    }
}
