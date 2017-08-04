package cz.deejay.molleculle.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class InputManager extends InputAdapter {

	OrthographicCamera camera;
	static Vector3 temp = new Vector3(); // temporary vector

	public InputManager(OrthographicCamera camera) {

		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		// set the left key status to pressed
		// if(keycode==Keys.LEFT){
		// GameManager.bob.setLeftPressed(true);
		// }
		// // set the right key status to pressed
		// else if(keycode==Keys.RIGHT){
		// GameManager.bob.setRightPressed(true);
		// }
		//
		// // make bob jump
		// else if(keycode==Keys.SPACE){
		// GameManager.bob.jump();
		// }
		//
		// // make bob shoot
		// else if(keycode==Keys.CONTROL_LEFT){
		// GameManager.bob.shoot();
		// }

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// set the left key status to pressed
		// if(keycode==Keys.LEFT){
		// GameManager.bob.setLeftPressed(false);
		// }
		// // set the right key status to pressed
		// else if(keycode==Keys.RIGHT){
		// GameManager.bob.setRightPressed(false);
		// }

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

}
