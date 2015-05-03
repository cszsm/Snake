package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import model.Board;
import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.06..
 */
public class BoardView extends RectangleView {
    private Board gameBoard;

    private Paint paintWall;
    private Paint paintFloor;

    public BoardView(int size, Board board) {
        fieldSize = size;
        gameBoard = board;

        paintWall = new Paint();
        paintWall.setStyle(Paint.Style.FILL);
        paintWall.setColor(Color.DKGRAY);

        paintFloor = new Paint();
        paintFloor.setStyle(Paint.Style.FILL);
        paintFloor.setColor(Color.LTGRAY);

        paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        for (int row = 0; row < 9; row++)
            for (int column = 0; column < 15; column++)
                drawField(canvas, column, row, gameBoard.getFields()[column][row]);
    }

    private void drawField(Canvas canvas, int column, int row, BoardElement element) {
        if (element == BoardElement.WALL)
            drawRectangle(column, row, canvas, paintWall);
        else
            drawRectangle(column, row, canvas, paintFloor);
        drawRectangle(column, row, canvas, paintBorder);
    }
}
