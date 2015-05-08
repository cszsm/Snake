package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import control.FoodManager;

/**
 * Created by Zsolt on 2015.03.07..
 * <p/>
 * Draws the food on the board
 */
public class FoodView extends RectangleView {

    private FoodManager foodManager;
    private Paint paintFood;

    /**
     * Sets the colors of the food
     */
    public FoodView(int size, FoodManager foodManager) {
        fieldSize = size;
        this.foodManager = foodManager;

        paintFood = new Paint();
        paintFood.setStyle(Paint.Style.FILL);
        paintFood.setColor(Color.YELLOW);
    }

    /**
     * Draws the food
     */
    public void draw(Canvas canvas) {
        drawRectangle(foodManager.getFood().getX(), foodManager.getFood().getY(), canvas, paintFood);
        drawRectangle(foodManager.getFood().getX(), foodManager.getFood().getY(), canvas, paintBorder);
    }
}
