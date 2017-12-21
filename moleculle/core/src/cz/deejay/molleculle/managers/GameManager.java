package cz.deejay.molleculle.managers;

import java.util.Date;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import cz.deejay.molleculle.GameConstants;
import cz.deejay.molleculle.GameData;
import cz.deejay.molleculle.GameScreen;
import cz.deejay.molleculle.GameScreen.GameState;
import cz.deejay.molleculle.utils.MapUtils;

public class GameManager {
	public static AssetManager assetManager;
	static TextureAtlas texturePack; // packed texture.

	public static TiledMap map;
	public static OrthogonalTiledMapRenderer renderer; // map renderer

	static ShapeRenderer shapeRenderer; // for drawing shapes

	static MapObjects mapObjects;

	public static int mapWidth;
	public static int mapHeight;

	public static float Width, Height;

	static BitmapFont font;

	public static long startDate;

	public static Rectangle door;
	public static short currentLevelIndex = 0;

	public static void initialize(float width, float height) {
		GameManager.Height = height;
		GameManager.Width = width;

		font = assetManager.get(GameConstants.fontPath);
		renderer = new OrthogonalTiledMapRenderer(map, GameConstants.unitScale);

		GameScreen.camera.setToOrtho(false, mapWidth, mapHeight);
		GameScreen.camera.update();
		// set the renderer's view to the game's main camera
		renderer.setView(GameScreen.camera);

		MapUtils.initialize(map);
		TextManager.initialize(width, height, font);

		// set the tiled map loader for the assetmanager
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

	}

	public static void renderBackground(SpriteBatch batch) {
		// draw the background

	}

	public static void renderGame(SpriteBatch batch) {
		batch.setProjectionMatrix(GameScreen.camera.combined);

		// if the viewport goes outside the map's dimensions update the camera's
		// position correctly
		if (!((GameScreen.camera.position.x - (GameScreen.camera.viewportWidth / 2)) > 0)) {
			GameScreen.camera.position.x = GameScreen.camera.viewportWidth / 2;
		} else if (((GameScreen.camera.position.x + (GameScreen.camera.viewportWidth / 2)) >= mapWidth)) {
			GameScreen.camera.position.x = mapWidth - GameScreen.camera.viewportWidth / 2;
		}

		renderer.setView(GameScreen.camera);
		GameScreen.camera.update();

		// draw the paddles with respect to hud cam
		batch.setProjectionMatrix(GameScreen.hudCamera.combined);

		TextManager.displayMessage(batch);

	}

	public static void dispose() {
		assetManager.clear();
	}

	public static void loadAssets1() {
		// queue the assets for loading
		assetManager.load(GameConstants.fontPath, BitmapFont.class);
		// set the tiled map loader for the assetmanager
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		// load the tiled map
		assetManager.load(GameConstants.levels[currentLevelIndex], TiledMap.class);
		// blocking method to load all assets
		assetManager.finishLoading();
	}

	static void drawShapes() {
		GameScreen.camera.update();

		shapeRenderer.setProjectionMatrix(GameScreen.camera.combined.scl(GameConstants.unitScale));

		// set the shape as an outline
		shapeRenderer.begin(ShapeType.Line);
		// set the shape's color as blue
		shapeRenderer.setColor(0, 1, 1, 1);

		Iterator<MapObject> mapObjectIterator = mapObjects.iterator();

		while (mapObjectIterator.hasNext()) {
			// get the map object from iterator
			MapObject mapObject = mapObjectIterator.next();

			// check if it is a rectangle
			if (mapObject instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
				// draw rectangle shape on the screen
				shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}

			// check if it is a polygon
			else if (mapObject instanceof PolygonMapObject) {
				Polygon polygon = ((PolygonMapObject) mapObject).getPolygon();

				shapeRenderer.polygon(polygon.getTransformedVertices());
			}

			// check if it an ellipse
			else if (mapObject instanceof EllipseMapObject) {
				Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();

				shapeRenderer.ellipse(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
			}

			// check if it is a polyline
			else if (mapObject instanceof PolylineMapObject) {
				Polyline polyline = ((PolylineMapObject) mapObject).getPolyline();

				shapeRenderer.polyline(polyline.getTransformedVertices());
			}
		}

		shapeRenderer.end();
	}

	static void setMapDimensions() {
		MapProperties properties = map.getProperties();
		mapHeight = Integer.parseInt(properties.get("height").toString());
		mapWidth = Integer.parseInt(properties.get("width").toString());
	}

	public static void loadLevel() {
		map = assetManager.get(GameConstants.levels[currentLevelIndex]);
		setMapDimensions();
		renderer.setMap(map);
		MapUtils.initialize(map);

		door = MapUtils.spawnDoor(mapWidth, mapHeight);

		GameScreen.camera.setToOrtho(false, mapWidth, mapHeight); // show
																	// specified
																	// units
																	// horizontally
																	// and
																	// vertically
																	// by
																	// the
																	// camera
		GameScreen.camera.update();
		GameScreen.gameState = GameState.LEVELLOADED;
		startDate = new Date().getTime();
	}

	public static void queueBaseAssets() {
		// queue the assets for loading
		assetManager.load(GameConstants.fontPath, BitmapFont.class);
	}

	public static void queueLevelAssets() {
		// load the tiled map
		assetManager.load(GameConstants.levels[currentLevelIndex], TiledMap.class);
	}

	public static void unloadLevelAssets() {
		assetManager.unload(GameConstants.levels[currentLevelIndex]);
	}

	public static void loadAssets() {
		if (assetManager.update()) {
			if (GameScreen.gameState == GameState.BASELOADING) {
				GameScreen.gameState = GameState.BASELOADED;
			} else {
				loadLevel();
				GameScreen.gameState = GameState.LEVELLOADED;
			}
		}
	}

}
