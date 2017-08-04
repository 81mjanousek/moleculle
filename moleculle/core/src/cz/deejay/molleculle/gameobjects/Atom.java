package cz.deejay.molleculle.gameobjects;

import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import cz.deejay.molleculle.GameConstants;
import cz.deejay.molleculle.managers.GameManager;
import cz.deejay.molleculle.utils.MapUtils;

public class Atom {
	
	public static final float RESIZE_FACTOR = 900f;
	public static final float ATOM_VELOCITY = 0.04f;
	public static int ANIMATION_FRAME_SIZE = 3;
	

	Set<Direction> directions; 
    Sprite sprite; // atom sprite
    Vector2 velocity; // velocity of the atom
    Rectangle rectangle; // rectangle object to detect collisions    
    

    public void render(SpriteBatch batch){
    	sprite.draw(batch);
    }  
    
    public void update(){        

    }
    
    public void checkWallHit(){
        // get the tiles from map utilities
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, sprite, "surface");     
        //if atom collides with any tile while moving right/left,reverse his horizontal motion
        for (Rectangle tile : tiles) {                
            if (rectangle.overlaps(tile)) {          
                velocity.x *=-1;
                break;
            }
        }
            
    }
    
	public Atom(float width, float height, TextureRegion atomSheet,
			float x, float y, Set<Direction> directions) {
		this.directions = directions;
        sprite = new Sprite(atomSheet);        
        sprite.setPosition(x, y);        
        sprite.setSize(sprite.getWidth() * (width /RESIZE_FACTOR),    (sprite.getHeight() * (width /RESIZE_FACTOR)) );
        sprite.setSize(sprite.getWidth() * GameConstants.unitScale,sprite.getHeight() * GameConstants.unitScale);
	}
    
}
