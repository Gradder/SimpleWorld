package com.gradder.simpleworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.concurrent.TimeUnit;

public class MainSimpleWorld extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TiledMap map;
	private BitmapFont font;
	private IsometricTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Vector2 lastCameraCoords;
	int x = 0;
	int y = 0;
	int reptX = 0;
	int reptY = 0;
	float cameraPositionX;
	float cameraPositionY;
	MapLayer layer;
	private Reptile reptya;

	@Override
	public void create() {
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");

		Gdx.input.setInputProcessor(new GestureDetector(new MapTouchListener()));
		font = new BitmapFont();
		camera = new OrthographicCamera();
		map = new TmxMapLoader().load("houseMap.tmx");
		renderer = new IsometricTiledMapRenderer(map, 1 / 64f);

		camera.setToOrtho(false, 30, 20);
		camera.update();
		lastCameraCoords = new Vector2(camera.position.x,camera.position.y);

		TiledMapTileSets tileSets = map.getTileSets();
		TiledMapTileSet lvl2 = tileSets.getTileSet(0);
		//System.out.println("					HERE:");
		layer = map.getLayers().get("lvl1");
		MapLayer layerObjects = map.getLayers().get("UNSTOPPABLE");
	//	layer.setVisible(false);
		System.out.println(layer);
		//System.out.println("					TO HERE");
		layerObjects.getObjects();

		reptya = new Reptile();
		reptya.position.set(20, 20);

	}

	@Override
	public void render() {
		camera.update();
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.position.set(++x,++y, 0);
		float deltaTime = Gdx.graphics.getDeltaTime();

		reptX++;
		reptY++;
		reptya.goToCoordinates(30,400);
		reptya.updateReptile(deltaTime, reptX, reptY);


		camera.update();
		renderer.setView(camera);
		renderer.render();
		//System.out.println("			cam: "+camera.position.x+"  "+camera.position.y);
		//	TiledMap map = new TmxMapLoader().load("map.tmx");
		batch.begin();
		//TiledMap map = new TmxMapLoader(new ExternalFileHandleResolver()).load("map.tmx");
		reptya.renderReptile(deltaTime, batch);
		//batch.draw(new  Texture("penguin.png"), 20, 20, 30, 30);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();

	}

	private Vector3 worldToIso(Vector3 point, int tileWidth, int tileHeight) {
		//gameCamera.unproject(point);
		point.x /= tileWidth;
		point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
		point.x -= point.y - point.x;
		return point;
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		map.dispose();
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
			camera.translate(-deltaX/64f,deltaY/64f);
			cameraPositionX = -deltaX/64f;
			cameraPositionY = deltaY/64f;

			return true;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {

			float zoom = camera.zoom + ((originalDistance - currentDistance)/(64f*100));
			if(zoom > 0.3 && zoom < 2) {
				camera.zoom =zoom;
			}
			return true;
		}

		@Override
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}

		@Override
		public void pinchStop() {

		}
	}
}

