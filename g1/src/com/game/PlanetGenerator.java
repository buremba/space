package com.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.logic.Player;

public class PlanetGenerator {
	ArrayList<Planet> planets;
	final float MAX_PLANET_RADIUS = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/5;
	final float MIN_PLANET_RADIUS = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/25;
	
	public PlanetGenerator(ArrayList<Player> players, int planetCount) {
		Vector2 origin = new Vector2(Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);
		
		planets.add(new Planet(origin, (float) Math.random()*MAX_PLANET_RADIUS/MIN_PLANET_RADIUS, (int) Math.random()));
		
		while(planets.size()<=planetCount) {
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
		    	  planets.add(new Planet(temporigin, (float) Math.random()*MAX_PLANET_RADIUS/MIN_PLANET_RADIUS, (int) Math.random()));
		      }
		    }
		}
		
	}
}
