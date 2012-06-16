package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.RenderList;

public class World {
	Texture texture;
	TextureRegion ucak;
	RenderList players=new RenderList();
	Vector2 dim;
	
	public World(int playerCount,Vector2 dim)
	{

	}
	public RenderList getPlayers() {
		return players;
	}
}
