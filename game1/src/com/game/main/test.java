package com.game.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class test  implements ApplicationListener {

	private Texture myTexture;

	@Override
	public void create() {
		FileHandle imageFileHandle = Gdx.files.internal("data/space1.png");
        myTexture = new Texture(imageFileHandle);
		
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
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
		myTexture.bind();
		
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
