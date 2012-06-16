package com.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Game implements ApplicationListener {
	Texture texture;	
	RenderList rlist;
	SpriteBatch s;
	ImageElement ie;
	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		ie=new ImageElement(new Vector2(100,100),new Vector2(100,100),new TextureRegion(texture,0,0,50,50));
		rlist=new RenderList();
		rlist.addObject(ie);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		if(Gdx.graphics.getDeltaTime()>1/30)
		{
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
	        rlist.Draw();
		}
		//Gdx.graphics.getGL10().glFlush();	
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
