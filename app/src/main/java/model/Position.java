package model;

import java.io.Serializable;

/**
 * Created by zscse on 2015. 11. 04..
 */
public class Position implements Serializable {
    public int x;
    public int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }
}
