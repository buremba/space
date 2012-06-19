package com.game.logic;

import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Drawable;
import com.game.ImageElement;

public class Rocket extends Drawable {
	public ImageElement image;
	public Vector2 speed = new Vector2(0,0);
	public Vector2 ivme;
	public double angle;
	public boolean isActive = false;
	private Sound launchSound = Gdx.audio.newSound(Gdx.files.internal("data/rocketTakeOff.mp3"));
	public final int MAX_FLY_SECONDS = 10;
	public int remainedSeconds;
	private Timer timer = new Timer();
	private Explode explosion = null;
	public boolean passPlayer;
	
	public Rocket(Vector2 pos, Vector2 dim)
	{
		this.image= new ImageElement(pos, dim , new TextureRegion(texture, 148, 124, 161, 125));
	}

	public void Draw(SpriteBatch s, World world) {
		if(this.passPlayer) {
			this.passPlayer = false;
			world.passPlayer();
		}
		
		if (isActive) {
			float angle = (float) Math.atan2(speed.y, speed.x);
			angle = (float) Math.toDegrees(angle);
			this.image.angle = angle + 180;
			this.image.position.x -= speed.x;
			this.image.position.y -= speed.y;

			for (int i = 0; i < world.planets.size(); i++) {
				float cosx = this.image.position.x - world.planets.get(i).origin.x;
				float sinx = this.image.position.y - world.planets.get(i).origin.y;
				float hip = (float) Math.hypot(cosx, sinx);
				if (hip < world.planets.get(i).radius) {
					this.stop(this.image.position);
					break;
				}
				speed.x += cosx * world.planets.get(i).radius / (hip * hip);
				speed.y += sinx * world.planets.get(i).radius / (hip * hip);
			}
			
			if (this.image.position.dst(world.getOrigin())>Math.hypot(Gdx.graphics.getHeight(), Gdx.graphics.getWidth())) {
				this.stop();
			}
			
			Player currentPlayer = world.getActivePlayer();
			for (int i = 0; i < world.getPlayers().size(); i++) {
				Player otherPlayer = world.getPlayers().get(i);
				if (otherPlayer != currentPlayer) {
					float cosx2 = otherPlayer.getCenterPos().x - this.image.getCenterPos().x;
					float sinx2 = otherPlayer.getCenterPos().y - this.image.getCenterPos().y;
					float hip2 = (float) Math.hypot(cosx2, sinx2);
					if (hip2 < 50) {
						this.stop(this.image.position);
						world.getPlayers().remove(i);
						break;
					}
				}
			}
			image.Draw(s);
			
		}
			
		if(explosion!=null) {
				explosion.Draw(s);
		}
	}
	
	public void launch() {
		this.isActive = true;
		launchSound.play();
		this.remainedSeconds = this.MAX_FLY_SECONDS;
		timer.schedule(new Task4Timer(this), 0, 1000);
	}
	
	public void stop() {
		this.isActive = false;
		this.passPlayer = true;
	}
	
	public void stop(Vector2 conflictPoint) {
		this.isActive = false;
		explosion = new Explode(conflictPoint, new Vector2(50, 50));
		this.passPlayer = true;
	}
	
	public boolean checkStatus() {
		if (this.remainedSeconds > 0) {
			this.remainedSeconds--;
			return true;
		} else {
			return false;
		}
	}
	@Override
	public void Draw(SpriteBatch s) {
		this.Draw(s, new World(0, new Vector2()));
	}
	

}

class Task4Timer extends TimerTask {
	Rocket rocket;

	public Task4Timer(Rocket r) {
		rocket = r;
	}

	public void run() {
		if(!rocket.isActive) {
			this.cancel();
		}else
		if(!rocket.checkStatus()) {
			rocket.stop();
			this.cancel();
		}
	}
}
