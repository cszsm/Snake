package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import model.Board;
import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.06..
 * <p/>
 * Draws the board
 */
public class BoardView extends RectangleView {

    private Board gameBoard;
    private Paint paintWall;
    private Paint paintFloor;

    /**
     * Sets the colors of the board
     */
    public BoardView(int size, Board board) {
        fieldSize = size;
        gameBoard = board;

        paintWall = new Paint();
        paintWall.setStyle(Paint.Style.FILL);
        paintWall.setColor(Color.DKGRAY);

        paintFloor = new Paint();
        paintFloor.setStyle(Paint.Style.FILL);
        paintFloor.setColor(Color.LTGRAY);
    }

    /**
     * Draws the board
     */
    public void draw(Canvas canvas) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 15; column++) {
                drawField(canvas, column, row, gameBoard.getFields()[column][row]);
            }
        }
    }

    /**
     * Draws a field on the board
     */
    private void drawField(Canvas canvas, int column, int row, BoardElement element) {
        if (element == BoardElement.WALL) {
            drawRectangle(column, row, canvas, paintWall);
        } else {
            drawRectangle(column, row, canvas, paintFloor);
        }

        drawRectangle(column, row, canvas, paintBorder);
    }
}
