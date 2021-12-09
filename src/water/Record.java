package water;

public class Record {
    private int x;
    private int y;
    private float time;

    public Record(int x, int y, float time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getTime() {
        return time;
    }
}
