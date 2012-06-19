package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Drawable {
	public Vector2 position,dim;
	public float angle;
	public Texture texture = new Texture(Gdx.files.internal("data/sprites.png"));
	public boolean sprite;
	public boolean visible;
	public String tag;
	public Drawable() {};
	public Drawable(Vector2 position,Vector2 dim,float angle,boolean sprite)
	{
		this.position=position;
		this.dim=dim;
		this.angle=angle;
		this.sprite=sprite;
		this.tag=null;
		this.visible=true;
	}
	public abstract void Draw(SpriteBatch s);
	public Vector2 getCenterPos()
	{
		return new Vector2(position.x,position.y);
	}
	public String toString() {
		return "[Drawable] Position: "+position.toString()+" Dimension: "+dim.toString();
	}
}