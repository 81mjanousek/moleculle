package cz.deejay.molleculle.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cz.deejay.molleculle.GameData;

public class TextManager {
	static BitmapFont font; // we draw the text to the screen using this
							// variable
	// viewport width and height
	static float width, height;
	static BitmapFont loadingFont; // we draw the text to the loading screen
									// using this variable
	static GlyphLayout layout = new GlyphLayout();

	public static void initialize(float width, float height, BitmapFont font) {
		TextManager.font = font;
		font = new BitmapFont();
		TextManager.width = width;
		TextManager.height = height;
		// set the font color to red
		font.setColor(Color.RED);
		// scale the font size according to screen width
		font.getData().setScale(width / 1000f);

	}

	public static void displayMessage(SpriteBatch batch) {
		if (GameData.runningTime == 1e+9) {
			GameData.time -= 1;
			GameManager.startTime = System.nanoTime();

		}
		layout.setText(font, "Score: " + GameData.score);
		float fontWidth = layout.width;
		// top the score display at top right corner
		font.draw(batch, "Score: " + GameData.score, width - fontWidth - width / 15f, height * 0.98f);
		// show the number of lives at top left corner
		font.draw(batch, "Time: " + GameData.minutes + ":" + GameData.seconds, width * 0.01f, height * 0.98f);

	}

	public static void initializeLoadingFont(float width, float height) {
		TextManager.width = width;
		TextManager.height = height;

		loadingFont = new BitmapFont();
		loadingFont.setColor(Color.RED);
		loadingFont.getData().setScale(width / 300f);
	}

	public static void displayLoadingMessage(SpriteBatch batch) {
		layout.setText(loadingFont, "Loading...");
		float fontWidth = layout.width;
		float fontHeight = layout.height;
		loadingFont.draw(batch, "Loading...", (width / 2) - fontWidth / 2, (height / 2) + fontHeight / 2);
	}

}
