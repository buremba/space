package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.ImageElement;

public class Explode {
	Texture texture;
	ImageElement[] imgs=new ImageElement[25];
	public int animCursor=0;
	public boolean finished=false;
	private Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("data/bomb.wav"));
	int geclimit=0;
	boolean isActive = false;
	public Explode(Vector2 pos,Vector2 dim)
	{
		texture = new Texture(Gdx.files.internal("data/explosion.png"));
		this.explosionSound.play();
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				imgs[i+j*5]=new ImageElement( pos,dim,new TextureRegion(texture,i*103,j*103,101,101));
			}
		}
	}
	public void setPos(Vector2 pos)
	{
		for(int i=0; i<25; i++)
		{
			imgs[i].position=pos;
		}
	}
	public void Draw(SpriteBatch s)
	{
		if(finished) {return;}
		else
		{
		imgs[animCursor].Draw(s);
		if(geclimit>3)
		{
			geclimit=0;
		animCursor=(animCursor+1)%imgs.length;
		}
		else
		{
			geclimit++;
		}
			if(animCursor==24)
			{
				finished=true;
				animCursor=0;
			}
		}
	}
}
