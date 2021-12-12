package water;

import java.io.*;
import java.util.Random;

/**
 * Class for generation data and write it to file
 */
public class DataGenerator {
    /**
     * random for random floats generation
     * filename contains target file name
     */
    private static Random random =  new Random();
    private final String filename;

    /**
     * @param filename contains target file name
     */
    public DataGenerator(String filename) {
        this.filename = filename;
    }

    /**
     * Actual data generation and writing in file
     * @param n wights of the field
     * @param m length of the field
     * @param leftLimit lower bound of data generation
     * @param rightLimit upper bound of data generation
     */
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
