package com.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.ImageElement;

public class Player extends Drawable {
	public ImageElement spaceship;
	public boolean isActive = false;
	TextureRegion[] spaceshipTypes = {new TextureRegion(texture,0,130,127,114)};
	public int type;
	
	public Player(Vector2 pos,Vector2 dim, int type){
		spaceship = new ImageElement(new Vector2(pos.x-(dim.x/2),pos.y-(dim.y/2)),dim,spaceshipTypes[type]);
		this.position = pos;
		this.dim = dim;
		this.type = type;
	}
	public Player(Vector2 pos,Vector2 dim, int type,  boolean activeStatus){
		this(pos, dim, type);
		isActive = activeStatus;
	}
	public Player(Vector2 pos,Vector2 dim){
		this(pos, dim, 0);
	}
	@Override
	public void Draw(SpriteBatch s) {
		spaceship.Draw(s);
	}

}
