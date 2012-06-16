package com.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.ImageElement;

public class Player extends Drawable {
	ImageElement ucak,roket;
	public Player(TextureRegion tr,Vector2 pos,Vector2 dim){
		ucak=new ImageElement(pos,dim,tr);
		ucak.tag="u�ak";
		
	}
	@Override
	public void Draw(SpriteBatch s) {
		// TODO Auto-generated method stub
		ucak.Draw(s);
		//roket.Draw(s);
	}
	public void launchRocket()
	{
		
	}
}
