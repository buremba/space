package com.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.logic.Roket;
import com.game.logic.World;

public class Game implements ApplicationListener,InputProcessor {
	Texture texture;	
	RenderList rlist;
	SpriteBatch s;
	ImageElement ie;
	private OrthographicCamera cam;
	private Rectangle glViewport;
	
	// User Inputs
	private Vector2 touchpoint;
	Boolean touchDown=false;
	Line ln;
	Roket rkt=null;
	
	String tagTurn="u�ak1";
	World world;
	int f=0;
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		rlist=new RenderList();
		texture = new Texture(Gdx.files.internal("data/sprites.png"));
		
		ie=new ImageElement(new Vector2(100,100),new Vector2(127,114),new TextureRegion(texture,0,130,127,114));
		ie.tag="u�ak1";
		rlist.addObject(ie);

		ie=new ImageElement(new Vector2(100,200),new Vector2(127,114),new TextureRegion(texture,0,130,127,114));
		ie.tag="u�ak2";
		rlist.addObject(ie);
	
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		touchpoint = new Vector2();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ln=new Line(new Vector2(163,157),new Vector2(0,0),new Vector3(255,0,0));
		ln.visible=true;
		ln.setLineWidth(5);
		s=new SpriteBatch();
		world=new World(3,new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	}
	@Override
	public void render() {		
		if(Gdx.graphics.getDeltaTime()>1/30)
		{
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glEnable(GL10.GL_LINEAR);
			gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
			cam.update();
			cam.apply(gl);

			rlist.Draw(cam);
			//world.getPlayers().Draw(cam);
			ln.visible=touchDown;
			ln.Draw(s);
		}
		Gdx.graphics.getGL10().glFlush();	
	}
	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		Drawable player=rlist.getObject(tagTurn);
		if(Math.hypot(player.position.x+player.dim.x/2-touchpoint.x, player.position.y+player.dim.y/2-touchpoint.y)<100)
		{
			touchDown=true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		ln.setPos2(touchpoint);
		Drawable player=rlist.getObject(tagTurn);
		
		double angle=0;
		double cosx=player.position.x+player.dim.x/2-touchpoint.x;
		double sinx=player.position.y+player.dim.y/2-touchpoint.y;
		angle=Math.atan2(sinx, cosx);
		angle=angle*180/Math.PI;

		angle+=180;
		player.angle=(float) angle;
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		touchDown=false;
		tagTurn=(tagTurn=="u�ak1"?"u�ak2":"u�ak1");
		ImageElement player=(ImageElement) rlist.getObject(tagTurn);
		ln.setPos1(player.getCenterPos());
		
		return false;
	}
	@Override public void resize(int arg0, int arg1) {	}
	@Override public void resume() {}
	@Override public boolean keyDown(int keycode) {
		if(keycode==Input.Keys.MINUS) {
			 cam.zoom += 0.06;
		}else
		if(keycode==Input.Keys.PLUS) {
			cam.zoom -= 0.06;
		}else
		if(keycode==Input.Keys.LEFT) {
           if (cam.position.x > 0)
                   cam.translate(-8, 0, 0);
	    }else
	    if(keycode==Input.Keys.RIGHT) {
	            if (cam.position.x < 1024)
	                    cam.translate(8, 0, 0);
	    }else
	    if(keycode==Input.Keys.DOWN) {
	            if (cam.position.y > 0)
	                    cam.translate(0, -8, 0);
	    }else
	    if(keycode==Input.Keys.UP) { // up
	            if (cam.position.y < 1024)
	                    cam.translate(0, 8, 0);
	    }
	    if(keycode==Input.Keys.Z) {
	            cam.rotate(-6, 0, 0, 1);
	    }
	    if(keycode==Input.Keys.X) {
	            cam.rotate(6, 0, 0, 1);
	    }
		return false;}
	@Override public boolean keyTyped(char arg0) {return false;}
	@Override public boolean keyUp(int arg0) {return false;}
	@Override public boolean scrolled(int arg0) {return false;}
	@Override public void dispose() {}
	@Override public void pause() {}
}
