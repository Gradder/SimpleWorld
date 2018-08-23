package com.gradder.simpleworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;


public class MainSimpleWorld extends ApplicationAdapter {

    private SpriteBatch fpsBatch;
    private SpriteBatch reptiliaBatch;

    private TiledMap map;
    private BitmapFont font;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private float cameraPositionX;
    private float cameraPositionY;
    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    MapLayer layer;
    private Matrix4 matrix4;
    private Reptile reptya;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new GestureDetector(new MapTouchListener()));

        initializeBatches();
        initializeMap();
        initializeCameraAndRenderer();
        initializeUnits();

        font = new BitmapFont();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tempMovingPenguin();

        matrix4 = camera.combined;
        matrix4.scl(0.01f);

        reptiliaBatch.setProjectionMatrix(matrix4);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        reptya.render(reptiliaBatch);

        fpsRender(fpsBatch);
    }

    @Override
    public void dispose() {
        fpsBatch.dispose();
        reptiliaBatch.dispose();
        map.dispose();
        renderer.dispose();
        font.dispose();
    }

    private class MapTouchListener implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            camera.translate(-deltaX / 64f, deltaY / 64f);
            cameraPositionX = -deltaX / 64f;
            cameraPositionY = deltaY / 64f;

            return true;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean zoom(float originalDistance, float currentDistance) {

			/*float zoom = camera.zoom + ((originalDistance - currentDistance)/(64f*100));
			if(zoom > 0.3 && zoom < 2) {
				camera.zoom =zoom;
			}
			return true;*/
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
            return false;
        }

        @Override
        public void pinchStop() {

        }
    }

    private void initializeBatches() {
        fpsBatch = new SpriteBatch();
        reptiliaBatch = new SpriteBatch();
    }

    private void initializeCameraAndRenderer() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.zoom = 0.3f;

        renderer = new IsometricTiledMapRenderer(map, 1 / 64f);
        renderer.setView(camera);

        camera.update();
    }

    private void initializeMap() {
        map = new TmxMapLoader().load("houseMap.tmx");
        //	layer = map.getLayers().get("lvl1");
        //	MapLayer layerObjects = map.getLayers().get("UNSTOPPABLE");
        //	layer.setVisible(false);
        //	layerObjects.getObjects();

    }

    private void initializeUnits() {
        initializeReptile(reptiliaBatch);
    }

    private void initializeReptile(SpriteBatch batch) {
        reptya = new Reptile();
        reptya.setPosition(2000, 500);
    }

    private void fpsRender(SpriteBatch fpsBatch) {
        fpsBatch.begin();
        font.draw(fpsBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        fpsBatch.end();
    }

    private void tempMovingPenguin() {
        reptya.setDestinationCoordinates(3000, 1000);
        reptya.tempWalking();
    }
}

