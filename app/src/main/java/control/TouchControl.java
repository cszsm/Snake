package control;

import android.view.MotionEvent;

/**
 * Created by Zsolt on 2015.03.07..
 *
 * Handles touch gestures
 */
public class TouchControl {
    private float lastDownX;
    private float lastDownY;

    private float differenceX;
    private float differenceY;

    /** Sets the coordinates of the last down action */
    public void setLastDown(MotionEvent event) {
        lastDownX = event.getX();
        lastDownY = event.getY();
    }

    /** Sets the coordinates of the last up action, and calculates the difference */
    public void setLastUp(MotionEvent event) {
        float lastUpX = event.getX();
        float lastUpY = event.getY();

        differenceX = lastUpX - lastDownX;
        differenceY = lastUpY - lastDownY;
    }

    /** Sets the direction based on the difference */
    public void setDirection(SnakeManager snakeManager) {
        if (Math.abs(differenceY) < Math.abs(differenceX)) {
            if (0 < differenceX)
                snakeManager.setRight();
            else if (differenceX < 0)
                snakeManager.setLeft();
        } else {
            if (0 < differenceY)
                snakeManager.setDown();
            else if (differenceY < 0)
                snakeManager.setUp();
        }
    }
}
