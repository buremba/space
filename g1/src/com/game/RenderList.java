package com.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderList {
	ArrayList<Drawable> list;
	SpriteBatch s;
	RenderList()
	{
		s=new SpriteBatch();
		list=new ArrayList<Drawable>();
	}
	public void addObject(Drawable object)
	{
		list.add(object);
	}
	public void Draw()
	{
		if(list.size()>0)
		{
			s.begin();
			for(int i=0; i<list.size(); i++)
			{
				list.get(i).Draw(s);
			}
			s.end();
		}
	}
}
