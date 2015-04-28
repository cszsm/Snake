package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import model.FoodManager;

/**
 * Created by Zsolt on 2015.03.07..
 */
public class FoodView extends RectangleView {

    private FoodManager foodManager;

    private Paint paintFood;

    public FoodView(int size, FoodManager foodManager) {
        fieldSize = size;
        this.foodManager = foodManager;

        paintFood = new Paint();
        paintFood.setStyle(Paint.Style.FILL);
        paintFood.setColor(Color.YELLOW);

        paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        drawRectangle(foodManager.getFood().getX(), foodManager.getFood().getY(), canvas, paintFood);
        drawRectangle(foodManager.getFood().getX(), foodManager.getFood().getY(), canvas, paintBorder);
    }
}
