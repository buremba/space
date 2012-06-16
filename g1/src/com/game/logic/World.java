package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.ImageElement;
import com.game.RenderList;

public class World {
	Texture texture;
	TextureRegion ucak;
	RenderList players=new RenderList();
	int cursorTurn=0;
	Vector2 dim;
	
	public World(int playerCount,Vector2 dim)
	{
		texture = new Texture(Gdx.files.internal("data/sprites.png"));		
		ucak=new TextureRegion(texture,0,130,127,114);
		float derFark=360/playerCount;
		for(int i=0; i<playerCount; i++)
		{
			float posx=(float) (dim.x/2+Math.cos(i*derFark*Math.PI/180)*50);
			float posy=(float) (Gdx.graphics.getHeight()-dim.y/2+Math.sin(i*derFark*Math.PI/180)*50);
			players.addObject(
					new ImageElement(new Vector2(posx,posy),new Vector2(127,114),new TextureRegion(texture,0,130,127,114)));

		}
	}
	public RenderList getPlayers() {
		return players;
	}
	public Drawable getPlayer()
	{
		return players.list.get(cursorTurn);
	}
	public void passPlayer()
	{
		cursorTurn=(cursorTurn+1)%players.list.size();
	}
}
