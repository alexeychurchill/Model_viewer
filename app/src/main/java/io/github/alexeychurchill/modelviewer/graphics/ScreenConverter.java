package io.github.alexeychurchill.modelviewer.graphics;

/**
 * Screen converter.
 */

public class ScreenConverter {
    private static final int DEFAULT_L = 20;
    private int l = DEFAULT_L;
    private int screenCenterX = 0;
    private int screenCenterY = 0;

    public int toScreenX(double x) {
        return (int) (screenCenterX + x * l);
    }

    public int toScreenY(double y) {
        return (int) (screenCenterY - y * l);
    }

    public void setScreenCenterY(int screenCenterY) {
        this.screenCenterY = screenCenterY;
    }

    public void setScreenCenterX(int screenCenterX) {
        this.screenCenterX = screenCenterX;
    }
}
