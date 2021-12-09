package water;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.RawHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import java.util.Scanner;

public class Main extends SimpleApplication {
    private DataReader dataReader;
    private DataGenerator dataGenerator;
    private float ultrasoundSpeed;
    private float[][] heights;

    private TerrainQuad terrain;
    Material mat_terrain;

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

    private void initializeUltrasoundSpeed(Main app) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter an ultrasound speed : ");
        app.ultrasoundSpeed = keyboard.nextFloat();
    }

    static int nextPowerOf2(int n) {
        int count = 0;

        if (n > 0 && (n & (n - 1)) == 0)
            return n;

        while(n != 0) {
            n >>= 1;
            count += 1;
        }

        return 1 << count;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(85);

        /** 1. Create terrain material and load four textures into it. */
        mat_terrain = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");

        /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
                "Textures/Terrain/splat/alphamap.png"));

        /** 1.2) Add GRASS texture into the red layer (Tex1). */
        Texture grass = assetManager.loadTexture(
                "Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);

        /** 1.3) Add DIRT texture into the green layer (Tex2) */
        Texture dirt = assetManager.loadTexture(
                "Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 32f);

        /** 1.4) Add ROAD texture into the blue layer (Tex3) */
        Texture rock = assetManager.loadTexture(
                "Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 128f);

        /** 2. Create the height map */

        int width = heights.length,
            length = heights[0].length;


        float[] newArray = new float[width * length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                newArray[i * width + j] = heights[i][j];
            }
        }

        RawHeightMap heightmap = new RawHeightMap(newArray);

        /** 3. We have prepared material and heightmap.
         * Now we create the actual terrain:
         * 3.1) Create a TerrainQuad and name it "my terrain".
         * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
         * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
         * 3.4) As LOD step scale we supply Vector3f(1,1,1).
         * 3.5) We supply the prepared heightmap itself.
         */
        int patchSize = 3;
        terrain = new TerrainQuad("water bottom", patchSize, width + 1, heightmap.getHeightMap());

        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, 0, 0);
        terrain.setLocalScale(3f, 3f, 3f);
        rootNode.attachChild(terrain);

        /** 5. The LOD (level of detail) depends on were the camera is: */
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
    }
}


