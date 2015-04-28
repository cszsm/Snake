package view;

/**
 * Created by Zsolt on 2015.04.02..
 */
public class ScreenResolution {
    private static ScreenResolution instance = null;

    int x;
    int y;

    protected ScreenResolution() {

    }

    public static ScreenResolution getInstance() {
        if(instance == null)
            instance = new ScreenResolution();
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
