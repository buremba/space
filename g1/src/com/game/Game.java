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
	ImageElement ie,roket,gezegen;
	Vector2 speed;
	
	private OrthographicCamera cam;
	private Rectangle glViewport;
	
	// User Inputs
	private Vector2 touchpoint;
	Boolean touchDown=false;
	Line ln;

	World world;
	int f=0;
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		texture = new Texture(Gdx.files.internal("data/sprites.png"));
	
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		touchpoint = new Vector2();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world=new World(3,new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
		ln=new Line(((ImageElement)world.getPlayer()).getCenterPos(),new Vector2(0,0),new Vector3(255,0,0));
		ln.visible=true;
		ln.setLineWidth(5);
		s=new SpriteBatch();
		Vector2 cpos=((ImageElement)world.getPlayer()).getCenterPos();
		ie=new ImageElement( 
				new Vector2(cpos.x-60,cpos.y-60),
				new Vector2(120,120),new TextureRegion(texture,309,0,120,120));
		roket=new ImageElement( 
				new Vector2(cpos.x-30,cpos.y-20),
				new Vector2(60,40),new TextureRegion(texture,148,124,161,125));
		gezegen=new ImageElement( 
				new Vector2(150,150),
				new Vector2(100,100),new TextureRegion(texture,151,0,126,124));
		speed=new Vector2(0,0);
		
	}
	@Override
	public void render() {		
		if(Gdx.graphics.getDeltaTime()>1/30)
		{
			///
			float angle=(float) Math.atan2(speed.y, speed.x);
			angle=(float) Math.toDegrees(angle);
			roket.angle=angle+180;
			roket.position.x-=speed.x;
			roket.position.y-=speed.y;
			float cosx=roket.position.x-gezegen.position.x;
			float sinx=roket.position.y-gezegen.position.y;
			float hip=(float) Math.hypot(cosx, sinx);
			speed.x+=cosx*75/(hip*hip);
			speed.y+=sinx*75/(hip*hip);
			///
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glEnable(GL10.GL_LINEAR);
			gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
			cam.update();
			cam.apply(gl);
			
			s.setProjectionMatrix(cam.combined);			
			s.begin();
			for(int i=0; i<world.getPlayers().list.size(); i++)
			{
				world.getPlayers().list.get(i).Draw(s);
			}
			ie.Draw(s);
			roket.Draw(s);
			gezegen.Draw(s);
			s.end();
			float pixdf=0;
			if(roket.position.y>Gdx.graphics.getHeight())
			{
				pixdf=roket.position.y-400;
				cam.zoom=1+pixdf/400;
				cam.position.y=roket.position.y-200;
			}
			else if(roket.position.y<Gdx.graphics.getHeight())
			{
				pixdf=400-roket.position.y;
				cam.zoom=1+pixdf/400;
				cam.position.y=roket.position.y+200;
			}
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
		Drawable player=world.getPlayer();
		if(
				Math.hypot(
						player.position.x+player.dim.x/2-touchpoint.x, 
						player.position.y+player.dim.y/2-touchpoint.y)<200)
		{
			touchDown=true;
			ln.setPos2(touchpoint);
			speed=new Vector2(0,0);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		if(touchDown)
		{
		ln.setPos2(touchpoint);
		Drawable player=world.getPlayer();
		
		double angle=0;
		double cosx=player.position.x+player.dim.x/2-touchpoint.x;
		double sinx=player.position.y+player.dim.y/2-touchpoint.y;
		angle=Math.atan2(sinx, cosx);
		angle=angle*180/Math.PI;

		angle+=180;
		player.angle=(float) angle;
		}
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int arg2, int arg3) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		touchDown=false;
		ImageElement player=(ImageElement) world.getPlayer();
		
		double angle=0;
		double cosx=player.position.x+player.dim.x/2-touchpoint.x;
		double sinx=player.position.y+player.dim.y/2-touchpoint.y;
		angle=Math.atan2(sinx, cosx);
		angle=angle*180/Math.PI;

		angle+=180;
		Vector2 cpos=((ImageElement)world.getPlayer()).getCenterPos();
		roket.position=new Vector2(cpos.x-30,cpos.y-20);
		roket.angle=(float) angle;		
		speed.x=(float) (cosx/50);
		speed.y=(float) (sinx/50);
		
		world.passPlayer();
		player=(ImageElement) world.getPlayer();
		ln.setPos1(player.getCenterPos());
		cpos=((ImageElement)world.getPlayer()).getCenterPos();
		ie.position=new Vector2(cpos.x-60,cpos.y-60);
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
