package com.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.ImageElement;

public class Roket extends Drawable {
	ImageElement ie;
	public Vector2 hiz;
	public Vector2 ivme;
	Roket(ImageElement ie,Vector2 force)
	{
		this.ie=ie;		
	}
	@Override
	public void Draw(SpriteBatch s) {
		// TODO Auto-generated method stub
		
		ie.Draw(s);
	}
}
