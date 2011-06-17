package pgle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.input.cameracontrol.FirstPersonCamera;
import org.peterbjornx.pgl2.model.Node;
import org.peterbjornx.pgl2.terrain.Terrain;
import org.peterbjornx.pgl2.terrain.TerrainSource;
import org.peterbjornx.pgl2.util.ServerMemoryManager;
import rs2.MapRegion;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/17/11
 * Time: 8:08 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class PglWrapper {

    protected Node scene;
    protected Camera camera;
    private FirstPersonCamera firstPersonCamera;
    private Terrain rsTerrain;
    private boolean running = false;
    private RsTerrainSource rsTerrainSource;

    /**
     * Initialize PGLEngine
     */
    public void initJgle() {
        try {
            int width = 640;
            int height = 480;
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            scene = new Node();
            camera = new Camera();
            camera.setViewport(new Vector2f(0, 0), new Vector2f(width, height));
            camera.setNearClip(1f);
            camera.setFarClip(128 * 30.0f);
            camera.setActive(true);
            scene.add(camera);
            firstPersonCamera = new FirstPersonCamera();
            glEnable(GL_DEPTH_TEST);
            running = true;
        } catch (LWJGLException e) {
            e.printStackTrace();
            running = false;
            //System.exit(0);
        }
    }

    public void loadNewRegion(MapRegion mapRegion){
        if (rsTerrain == null){
            rsTerrainSource = new RsTerrainSource(mapRegion);
            rsTerrainSource.updateMap();
            rsTerrain = new Terrain(rsTerrainSource);
            scene.add(rsTerrain);
        } else {
            rsTerrainSource.updateMap();
            rsTerrain.update();
        }
    }

    /**
     * The entry point for this SimpleApplication
     */
    public void process() {
        if (!running)
            return;
        if (!Display.isCloseRequested()) {
            preRender();
            scene.render(null);
            Display.update();
            ServerMemoryManager.processQueues();
        } else {
            Display.destroy();
            running = false;
        }
    }

    public void preRender() {
        firstPersonCamera.handleInput(camera);
    }

}
