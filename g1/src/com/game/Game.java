package com.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.logic.Player;
import com.game.logic.World;

public class Game implements ApplicationListener, InputProcessor {
	SpriteBatch s;

	private OrthographicCamera cam;
	private Rectangle glViewport;

	private Music duelMusic;

	Boolean touchDown = false;
	Line ln;
	World world;


	Vector3 cameraPosBackup;
	float cameraZoomBackup;

	BitmapFont font;
	Stage ui;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world = new World(3, new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		ln = new Line(world.getActivePlayer().getCenterPos(), new Vector2(0, 0), new Vector3(255, 0, 0));
		ln.visible = true;
		ln.setLineWidth(5);
		s = new SpriteBatch();
		
		cameraPosBackup = cam.position;
		cameraZoomBackup = cam.zoom;

		font = new BitmapFont();

		ui = new Stage(480, 320, false);
		Label fps = new Label("fps: 0", new Label.LabelStyle(font, Color.WHITE), "fps");
		fps.x = 10;
		fps.y = 30;
		fps.color.set(0, 1, 0, 1);
		fps.scaleX = 2;
		fps.scaleY = 2;
		ui.addActor(fps);
		loadSounds();
	}

	private void loadSounds() {
		duelMusic = Gdx.audio.newMusic(Gdx.files.internal("data/duelFates.mp3"));

		// start the playback of the background music immediately
		duelMusic.setLooping(true);
		duelMusic.play();
	}
	
	public boolean passPlayers() {
		world.passPlayer();
		/*
		Vector2 cpos = world.getActivePlayer().getCenterPos();
		cam.zoom = cameraZoomBackup;
		cam.position.x = cpos.x;
		cam.position.y = cpos.y;
		world.rocket.speed.x = 0;
		world.rocket.speed.y = 0;
		*/
		return true;
	}

	@Override
	public void render() {
		if (Gdx.graphics.getDeltaTime() < 1 / 30)
			return;

		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_LINEAR);
		gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
		cam.update();
		cam.apply(gl);
		s.setProjectionMatrix(cam.combined);
		
		if (world.rocket.isActive) {
			cam.zoom = 1f + ((float) world.getOrigin().dst(world.rocket.image.position) / 500);
			cam.position.x = world.rocket.image.position.x;
			cam.position.y = world.rocket.image.position.y;
			
			((Label)ui.findActor("fps")).setText("Kalan Saniye : " + world.rocket.remainedSeconds);
		}else
		if(world.rocket.passPlayer) {
			cam.zoom = cameraZoomBackup;
			cam.position.x = 400;
			cam.position.y = 300;
		}
		
		s.begin();
		world.Draw(s);
		s.end();

		ln.visible = touchDown;
		ln.Draw(s);

		ui.draw();
		
		Gdx.graphics.getGL10().glFlush();
	}

	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		Vector2 touchpoint = adjustPoint(x,y);
		
		Drawable player = world.getActivePlayer();
		if (Math.hypot(player.position.x + player.dim.x / 2 - touchpoint.x, player.position.y + player.dim.y / 2 - touchpoint.y) < 200) {
			touchDown = true;
			ln.setPos2(touchpoint);
			world.rocket.speed = new Vector2(0, 0);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		Vector2 touchpoint = adjustPoint(x,y);
		
		if (touchDown) {
			ln.setPos2(touchpoint);
			Player player = world.getActivePlayer();
			ln.setPos1(player.getCenterPos());

			double angle = 0;
			double cosx = player.position.x + player.dim.x / 2 - touchpoint.x;
			double sinx = player.position.y + player.dim.y / 2 - touchpoint.y;
			angle = Math.atan2(sinx, cosx);
			angle = angle * 180 / Math.PI;

			angle += 180;
			player.spaceship.angle = (float) angle;
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		//System.out.println(adjustPoint(x,y));
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int arg2, int arg3) {
		Vector2 touchpoint = adjustPoint(x,y);
		
		touchDown = false;
		Player player = world.getActivePlayer();

		double angle = 0;
		double cosx = player.spaceship.position.x + player.spaceship.dim.x / 2 - touchpoint.x;
		double sinx = player.spaceship.position.y + player.spaceship.dim.y / 2 - touchpoint.y;
		angle = Math.atan2(sinx, cosx);
		angle = angle * 180 / Math.PI;

		angle += 180;
		Vector2 cpos = world.getActivePlayer().getCenterPos();
		world.rocket.image.position = new Vector2(cpos.x - 30, cpos.y - 20);
		world.rocket.image.angle = (float) angle;
		world.rocket.speed.x = (float) (cosx / 25);
		world.rocket.speed.y = (float) (sinx / 25);
		world.rocket.launch();

		return false;
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.MINUS) {
			cam.zoom += 0.06;
		} else if (keycode == Input.Keys.PLUS) {
			cam.zoom -= 0.06;
		} else if (keycode == Input.Keys.LEFT) {
			if (cam.position.x > 0)
				cam.translate(-8, 0, 0);
		} else if (keycode == Input.Keys.RIGHT) {
			if (cam.position.x < 1024)
				cam.translate(8, 0, 0);
		} else if (keycode == Input.Keys.DOWN) {
			if (cam.position.y > 0)
				cam.translate(0, -8, 0);
		} else if (keycode == Input.Keys.UP) { // up
			if (cam.position.y < 1024)
				cam.translate(0, 8, 0);
		}
		if (keycode == Input.Keys.Z) {
			cam.rotate(-6, 0, 0, 1);
		}
		if (keycode == Input.Keys.X) {
			cam.rotate(6, 0, 0, 1);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public void dispose() {	
	}

	@Override
	public void pause() {
	}
	
	private Vector2 adjustPoint(float x, float y) {
		Vector3 touchpointv3 = new Vector3(x, y, 0);
		cam.unproject(touchpointv3);
		return new Vector2(touchpointv3.x, touchpointv3.y);
	}
}
