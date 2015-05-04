package model;

import android.graphics.Point;

import java.util.Random;

import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.07..
 *
 * Represents a food in the game
 */
public class Food {
    private int x;
    private int y;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
