package com.game.logic;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.ImageElement;
import com.game.Planet;

public class World {
	Texture texture;
	private ImageElement background;
	private ImageElement pointer;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Planet> planets = new ArrayList<Planet>();
	public Rocket rocket = new Rocket(new Vector2(), new Vector2(60, 40));
	int cursorTurn = 0;
	Vector2 dim;
	
	public World(int playerCount,Vector2 dim)
	{	
		float derFark=360/playerCount;
		for(int i=0; i<playerCount; i++)
		{
			float der=(float) Math.toRadians(i*derFark);
			float posx=(float) (dim.x/2+Math.cos(der)*(dim.x-dim.x*70/100+50)-63);
			float posy=(float) (dim.y/2+Math.sin(der)*(dim.y-dim.y*70/100+50)-57);
			Player player = new Player(new Vector2(posx,posy), new Vector2(64,56));
			players.add(player);
		}
		this.backgroundGenerator();
		this.planetGenerator(1);
		pointer = new ImageElement( this.players.get(cursorTurn).position, new Vector2(120,120),new TextureRegion(new Texture(Gdx.files.internal("data/sprites.png")),309,0,120,120));
		
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Player getActivePlayer() {
		return this.players.get(cursorTurn);
	}
	public int getClosestDistance(Vector2 point) {
		int distance = -1;
		@SuppressWarnings("unused")
		int index = 0;
		for(int i=0; i<players.size(); i++) {
			if(distance>point.dst(players.get(i).position) || distance==-1) {
				distance = (int) point.dst(players.get(i).position);
				index = i;
			}
		}
		return distance;
	}
	public Vector2 getOrigin() {
		return new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
	public void planetGenerator(int planetCount) {
		final float MAX_PLANET_RADIUS = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/5;
		final float MIN_PLANET_RADIUS = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/25;
		
		planets.add(new Planet(this.getOrigin(), (float) ((float) MIN_PLANET_RADIUS+(Math.random()*MAX_PLANET_RADIUS-MIN_PLANET_RADIUS)), (int) Math.random()));
		
		while(planets.size()<planetCount) {
			Vector2 temporigin = new Vector2((float) Math.random()*Gdx.graphics.getWidth(), (float) Math.random()*Gdx.graphics.getHeight());
			boolean conflict= false;
			
			Iterator<Planet> itr = planets.iterator();
		    while (itr.hasNext()) {
		      Planet element = itr.next();
		      if(element.origin.x-element.radius<temporigin.x && element.origin.x+element.radius>temporigin.x && 
		    		  element.origin.y-element.radius<temporigin.y && element.origin.y+element.radius<temporigin.y) {
		    	  conflict = true;
		    	  break;
		      }
		      if(!conflict) {
		    	  planets.add(new Planet(temporigin, (float) ((float) MIN_PLANET_RADIUS+(Math.random()*MAX_PLANET_RADIUS-MIN_PLANET_RADIUS)), (int) Math.random()));
		      }
		    }
		}
	}
	public void passPlayer()
	{
		cursorTurn=(cursorTurn+1)%players.size();
		this.pointer.position = this.players.get(cursorTurn).position;
	}
	public void backgroundGenerator() {
		background = new ImageElement(
				new Vector2(-Gdx.graphics.getWidth() / 2,-Gdx.graphics.getHeight() / 2),
				new Vector2(Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() * 2),
				new TextureRegion(new Texture(Gdx.files.internal("data/stars.jpg")), 0, 0, 256, 256));
	}
	
	public void Draw(SpriteBatch s) {
		background.angle += 0.2f;
		background.Draw(s);
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).Draw(s);
		}
		
		for(int i=0; i<planets.size(); i++) {
			planets.get(i).Draw(s);
		}
		
		pointer.Draw(s);
		
		rocket.Draw(s, this);
	}
	
	
}
