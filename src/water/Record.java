package water;

/**
 * Class that contains information about time
 * that required time for ultrasound to reach
 * the bottom of the pond
 */
public class Record {
    /**
     * x is x coordinate relatively the pond
     * y is y coordinate relatively the pond
     * time is the actual time required by ultrasound to reach the bottom of the pond
     * and back to the locator
     */
    private int x;
    private int y;
    private float time;

    /**
     *
     * @param x is x coordinate relatively the pond
     * @param y is y coordinate relatively the pond
     * @param time is the actual time required by ultrasound to reach the bottom of the pond
     * and back to the locator
     */
    public Record(int x, int y, float time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    /**
     * Getter for x
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for time
     * @return time required by ultrasound to reach the bottom of the pond
     * and back to the locator
     */
    public float getTime() {
        return time;
    }
}
