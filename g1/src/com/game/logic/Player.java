package com.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Drawable;
import com.game.ImageElement;

public class Player extends Drawable {
	ImageElement ucak,roket;
	Player(ImageElement ie){
		this.ucak=ie;
	}
	public void roket(float x,float y,float angle){
		
	}
	@Override
	public void Draw(SpriteBatch s) {
		// TODO Auto-generated method stub
		ucak.Draw(s);
		if(roket!=null)
		{
			roket.Draw(s);
		}
	}
}
