package hr.fer.zemris.java.gui.charts;

/**
 * This class represents xy values.
 */
public class XYValue {

    /**
     * X value.
     */
    private int x;

    /**
     * Y value.
     */
    private int y;

    /**
     * Public constructor.
     *
     * @param x value
     * @param y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x value.
     *
     * @return X value
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y value.
     *
     * @return Y value
     */
    public int getY() {
        return y;
    }
}