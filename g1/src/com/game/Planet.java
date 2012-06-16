package com.game;

import com.badlogic.gdx.math.Vector2;

public class Planet {
	public Vector2 origin;
	public float radius;
	public int type;

	public Planet(Vector2 origin, float radius, int type) {
		this.origin = origin;
		this.radius = radius;
		this.type = type;
	}
}
