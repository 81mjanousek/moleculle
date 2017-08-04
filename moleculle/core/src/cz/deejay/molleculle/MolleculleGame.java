package cz.deejay.molleculle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cz.deejay.molleculle.GameScreen;

public class MolleculleGame extends Game {
	
	@Override
	public void create() {
	 setScreen(new GameScreen(this));
	}

}
