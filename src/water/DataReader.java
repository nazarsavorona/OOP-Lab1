package water;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;

public class DataReader {
    private String filename;
    private List<Record> data;
    private int width;
    private int length;

    public DataReader(String filename) {
        this.filename = filename;
        this.data = new ArrayList<Record>();
        this.width = 0;
        this.length = 0;
    }

    public void readData() {
        Scanner sc = null;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().trim().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);

            data.add(new Record(x, y, Float.parseFloat(line[2])));

            width = max(width, x + 1);
            length = max(length, y + 1);
        }
    }

    public float[][] transform(float ultrasoundSpeed) {
        float[][] result = new float[width][length];

        for (float[] subArray : result) {
            Arrays.fill(subArray, 0);
        }
        for (Record record : data) {
            result[record.getX()][record.getY()] = record.getTime() * (-1f) * ultrasoundSpeed / 2;
        }

        return result;
    }
}
