package com.game.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ahmet.polyshaping.Rect;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Game implements ApplicationListener,InputProcessor,Runnable {
	Rect kutu;
	SpriteBatch batch;
	
	private OrthographicCamera cam;
	private Rectangle glViewport;
	
    static Socket clientSocket = null;
    static PrintStream os = null;
    static DataInputStream is = null;
    static BufferedReader inputLine = null;
    static boolean closed = false;
    Gson gson=new Gson();
    static int myid=0;
    float posX=0;
	
	private Sound rocketSound;
	private Sound explosionSound;
	private Music duelMusic;
	
    //ArrayList<Rect> diger=new ArrayList<Rect>();
    //static Hashtable diger = new Hashtable(); 
    static Map<Integer,Rect> diger=new HashMap<Integer,Rect>();
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		batch=new SpriteBatch();
		kutu=new Rect(new Vector2(100,100), new Vector2(100,100), new Vector3(0,0,255), true);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//connect();

		loadSounds();
	}
	
	/**
	 * Load sounds and backround music
	 */
	private void loadSounds() {
		rocketSound = Gdx.audio.newSound(Gdx.files.internal("data/rocketTakeOff.mp3"));
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("data/explode.mp3"));
		duelMusic = Gdx.audio.newMusic(Gdx.files.internal("data/duelFates.mp3"));

		// start the playback of the background music immediately
		duelMusic.setLooping(true);
		duelMusic.play();
	}
	
	public void connect()
	{
		int port_number=2222;
        String host="192.168.0.10";
	// Initialization section:
	// Try to open a socket on a given host and port
	// Try to open input and output streams
	try {
            clientSocket = new Socket(host, port_number);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host "+host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "+host);
        }
	
    if (clientSocket != null && os != null && is != null) {
        new Thread(new Game()).start();
    }
	}
	@Override
	public void render() {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);

		cam.update();
		cam.apply(gl);
		batch.setProjectionMatrix(cam.combined);
		posX+=2;
		posX=posX%Gdx.graphics.getWidth();
		kutu.setPosition(new Vector2(posX,kutu.getPosition().y));
		kutu.Draw(batch);
        Set s=diger.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            int key=(Integer)m.getKey();
            Rect value=(Rect)m.getValue();
            value.Draw(batch);
        }
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		/*os.close();
		try {
			is.close();
			clientSocket.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub		
	}
	@Override
	public boolean keyDown(int arg0) {
		
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
    public void run() {		
	String responseLine;
	try{ 
	    while ((responseLine = is.readLine()) != null) {
			System.out.println(responseLine);
			if(responseLine.indexOf("$")!=-1)
			{
				myid=Integer.parseInt(responseLine.substring(1));
				System.out.println("----------ID REGISTERED---------");
			}
			else
			{
				dataOb obj = gson.fromJson(responseLine, dataOb.class);
				if(diger.get(obj.id)==null)
				{					
				this.diger.put(obj.id,new Rect(
						new Vector2(obj.x,obj.y), 
						new Vector2(100,100), new Vector3(0,255,255), true));
				}
				else
				{
					diger.get(obj.id).setPosition(new Vector2(obj.x,obj.y));
				}
			}
			if (responseLine.indexOf("*** Bye") != -1) break;
	    }
            closed=true;
	} catch (IOException e) {
	    System.err.println("IOException:  " + e);
	}
    }
	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		/*Vector3 t3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(t3);
		kutu.setPosition(new Vector2(t3.x,t3.y));
        if (clientSocket != null && os != null && is != null) {
            os.println(gson.toJson(new dataOb(myid,t3.x,t3.y)));
        }*/
		return false;
	}
	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		/*Vector3 t3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(t3);
		kutu.setPosition(new Vector2(t3.x,t3.y));
        if (clientSocket != null && os != null && is != null) {
            os.println(gson.toJson(new dataOb(myid,t3.x,t3.y)));
        }*/
		return false;
	}
	@Override
	public boolean touchMoved(int x, int y) {

		return false;
	}
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
