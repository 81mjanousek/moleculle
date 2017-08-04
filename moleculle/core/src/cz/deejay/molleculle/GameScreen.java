package cz.deejay.molleculle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cz.deejay.molleculle.managers.GameManager;
import cz.deejay.molleculle.managers.InputManager;
import cz.deejay.molleculle.managers.TextManager;

public class GameScreen implements Screen {

	MolleculleGame game;
	SpriteBatch batch; // spritebatch for drawing
	public static OrthographicCamera camera;
	public static OrthographicCamera hudCamera;

	public enum GameState {
		BASELOADING, BASELOADED, LEVELLOADING, LEVELLOADED
	};

	public static GameState gameState = GameState.BASELOADING;

	float width, height;

	public GameScreen(MolleculleGame game) {
		this.game = game;
		// get window dimensions and set our viewport dimensions
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		// set our camera viewport to window dimensions
		camera = new OrthographicCamera(width, height);
		// center the camera at w/2,h/2
		camera.setToOrtho(false);

		batch = new SpriteBatch();
		// set our hud camera's viewport to window dimensions
		hudCamera = new OrthographicCamera(width, height);
		// center the camera at w/2,h/2
		hudCamera.setToOrtho(false);

		GameManager.assetManager = new AssetManager();
		TextManager.initializeLoadingFont(width, height);
		GameManager.queueBaseAssets();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		switch (gameState) {

		case BASELOADING:
			GameManager.loadAssets();
			batch.setProjectionMatrix(hudCamera.combined);
			batch.begin();
			TextManager.displayLoadingMessage(batch);
			batch.end();
			break;

		case BASELOADED:

			// initialize the game
			GameManager.initialize(width, height);
			Gdx.input.setInputProcessor(new InputManager(hudCamera)); // enable InputManager to receive input events
			GameManager.queueLevelAssets();
			gameState = GameState.LEVELLOADING;
			break;

		case LEVELLOADING:
			GameManager.loadAssets();
			batch.setProjectionMatrix(hudCamera.combined);
			batch.begin();
			TextManager.displayLoadingMessage(batch);
			batch.end();
			break;

		case LEVELLOADED:
			// set the spritebatch's drawing view to the hud camera's view
			batch.setProjectionMatrix(hudCamera.combined);

			batch.begin();
			GameManager.renderBackground(batch);
			batch.end();

			// set the renderer's view to the game's main camera
			GameManager.renderer.setView(camera);
			GameManager.renderer.render();

			batch.begin();
			GameManager.renderGame(batch);
			batch.end();

		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// dispose the batch and the textures
		batch.dispose();
		GameManager.dispose();
	}
}
