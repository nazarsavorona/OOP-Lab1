package water;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.RawHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import java.util.Scanner;


/**
 * Main class with terrain generation
 */
public class Main extends SimpleApplication {
    /**
     * dataReader for reading data from file
     * dataGenerator for generating random data in file
     * ultrasoundSpeed contains ultrasound speed value
     * heights contains height of all area of terrain
     * terrain contains all data of terrain
     * material contains current material for each texture
     */
    private DataReader dataReader;
    private DataGenerator dataGenerator;
    private float ultrasoundSpeed;
    private float[][] heights;

    private TerrainQuad terrain;
    Material material;


    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        String filename = "data.txt";
        Main app = new Main();

        app.dataGenerator = new DataGenerator(filename);
        app.dataReader = new DataReader(filename);
        app.initializeUltrasoundSpeed(app);

        app.dataGenerator.generateData(2, 2, 0.001f, 0.01f);
        app.dataReader.readData();
        app.heights = app.dataReader.transform(app.ultrasoundSpeed);

        app.start();
    }

    /**
     * Ultra sound speed initialization
     * @param app application variable
     */
    private void initializeUltrasoundSpeed(Main app) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter an ultrasound speed : ");
        app.ultrasoundSpeed = keyboard.nextFloat();
    }

    /**
     * Initialization of the app
     */
    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(85);

        material = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");

        material.setTexture("Alpha", assetManager.loadTexture(
                "Textures/Terrain/splat/alphamap.png"));

        Texture grass = assetManager.loadTexture(
                "Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        material.setTexture("Tex1", grass);
        material.setFloat("Tex1Scale", 64f);

        Texture dirt = assetManager.loadTexture(
                "Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        material.setTexture("Tex2", dirt);
        material.setFloat("Tex2Scale", 32f);

        Texture rock = assetManager.loadTexture(
                "Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        material.setTexture("Tex3", rock);
        material.setFloat("Tex3Scale", 128f);

        int width = heights.length,
                length = heights[0].length;


        float[] newArray = new float[width * length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                newArray[i * width + j] = heights[i][j];
            }
        }

        RawHeightMap heightmap = new RawHeightMap(newArray);

        int patchSize = 3;
        terrain = new TerrainQuad("water bottom", patchSize, width + 1, heightmap.getHeightMap());

        terrain.setMaterial(material);
        terrain.setLocalTranslation(0, 0, 0);
        terrain.setLocalScale(3f, 3f, 3f);
        rootNode.attachChild(terrain);

        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
    }
}


