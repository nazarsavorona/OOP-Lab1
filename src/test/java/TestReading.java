package test.java;

import org.junit.Assert;
import org.junit.Test;
import water.DataGenerator;
import water.DataReader;

public class TestReading {
    @Test
    public void testGenerating() {
        String filename = "test.txt";
        float ultrasoundSpeed = 100f;

        int n = 5;
        int m = 5;

        float leftLimit = 0.001f;
        float rightLimit = 0.01f;

        DataGenerator dataGenerator = new DataGenerator(filename);
        DataReader dataReader = new DataReader(filename);

        dataGenerator.generateData(n, m, leftLimit, rightLimit);
        dataReader.readData();
        float[][] heights = dataReader.transform(ultrasoundSpeed);

        Assert.assertEquals(heights.length, n);
        Assert.assertEquals(heights[0].length, m);

        for(float[] currentHeights : heights) {
            for(float height : currentHeights) {
                Assert.assertTrue(height > rightLimit * (-1f) * ultrasoundSpeed / 2);
                Assert.assertTrue(height < leftLimit * (-1f) * ultrasoundSpeed / 2);
            }
        }
    }
}
