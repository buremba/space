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
			float der=(float) Math.toRadians(i*derFark);
			float posx=(float) (dim.x/2+Math.cos(der)*(dim.x-dim.x*70/100)-63);
			float posy=(float) (dim.y/2+Math.sin(der)*(dim.y-dim.y*70/100)-57);
			ImageElement ie=new ImageElement(new Vector2(posx,posy),new Vector2(63,57),new TextureRegion(texture,0,130,127,114));
			ie.angle=i*derFark+180;
			players.addObject(ie);
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
