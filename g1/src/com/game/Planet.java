package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Planet extends Drawable{
	public Vector2 origin;
	public float radius;
	public int type;
	private ImageElement image;

	public Planet(Vector2 origin, float radius, int type) {
		image = new ImageElement(origin,new Vector2(radius*2, radius*2), new TextureRegion(super.texture, 151, 0, 126, 124));
		this.origin = origin;
		this.radius = radius;
		this.type = type;
	}

	@Override
	public void Draw(SpriteBatch s) {
		image.Draw(s);
	}
}
