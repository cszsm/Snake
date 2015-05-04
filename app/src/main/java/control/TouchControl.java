package control;

import android.view.MotionEvent;

/**
 * Created by Zsolt on 2015.03.07..
 */
public class TouchControl {
    private float lastDownX;
    private float lastDownY;

    private float differenceX;
    private float differenceY;

    public void setLastDown(MotionEvent event) {
        lastDownX = event.getX();
        lastDownY = event.getY();
    }

    public void setLastUp(MotionEvent event) {
        float lastUpX = event.getX();
        float lastUpY = event.getY();

        differenceX = lastUpX - lastDownX;
        differenceY = lastUpY - lastDownY;
    }

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
