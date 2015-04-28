package model;

import model.enumeration.BoardElement;

/**
 * Created by Zsolt on 2015.03.06..
 */
public class Board {
    private BoardElement[][] fields;

    public Board() {
        fields = new BoardElement[16][9];

        for(int row = 0; row < 9; row++)
            for(int column = 0; column < 15; column++)
                if(row == 0 || row == 8 || column == 0 || column == 14)
                    fields[column][row] = BoardElement.WALL;
                else
                    fields[column][row] = BoardElement.FLOOR;
    }

    public BoardElement[][] getFields() {
        return fields;
    }
}
