package water;

import java.io.*;
import java.util.Random;

public class DataGenerator {
    private static Random random =  new Random();
    final private String filename;

    public DataGenerator(String filename) {
        this.filename = filename;
    }

    public void generateData(int n, int m, float leftLimit, float rightLimit) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                printWriter.printf("%d %d %f\n", i, j, leftLimit  + random.nextFloat() * (rightLimit - leftLimit));
            }
        }
        printWriter.close();
    }
}
