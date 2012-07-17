using UnityEngine;
using System.Collections;

public class GameManager : MonoBehaviour {
	public Ship[] players;
	public int turn;
	
	public GameObject currentMissile;
	public CameraController camera;
	
	public float maxDist=50;
	public float fixedZoom=30;
	
	public int maxHit=3;
	
	/* TODO: this two will removed. */
	public GameObject player;
	public bool playerTurn=false;
	
	public GameObject selectCircle; /* reference of select circle :S */
	
	public float trailTimeOut=100;
	
	/* object that will be cloned for trailing */
	public GameObject trail;
	
	/* skin */
	public GUISkin skin;
	
	private bool showMisilleSelect=true;
	
	/* array of missile that can useable for players */
	public GameObject [] missiles;
	
	/* last trail. using for remove after next shoot */
	private GameObject lastTrail;
	
	/* using for objects can use actual game manager */
	public static GameManager gm;
	
	void Start () {
		gm=this;
		
		camera.lookAt(player.transform.position, fixedZoom);
		
		exitDrag();
	}
	
	void Update () {
		if(!playerTurn){ // this means missile on way
			float dist= (currentMissile.transform.position - player.transform.position).magnitude;
			
			camera.setZoom(dist*2.5f);
			
			if(dist > maxDist){
				cancelMissile();
			}
		}
		
	}
	
	void OnGUI(){
		GUI.skin=skin;
		
		/*GUI.Label(new Rect(10, 10, 100, 20), "Points: "+player.GetComponent<Ship>().hit);*/
		
					
		int width= 400;
		int height=150;
		
		int insideWidth=width-40;
		
		if(playerTurn){
			if(player.GetComponent<Ship>().missile==null){
				
				
				GUI.Window(0, new Rect(Screen.width/2-width/2, Screen.height/2, width, 80), this.misilleWarning, "");
			}
			
			/*GUI.Label(new Rect(10, 40, 100, 20), "Players Turn");*/
			
			if(showMisilleSelect)
				GUI.Window(1, new Rect (Screen.width/2-width/2 , 10 , width, height+10), this.missileSelectMenu, "");
			else{
				width-=40;
				if(GUI.Button(new Rect (Screen.width/2-insideWidth/2 , 30 , insideWidth, 40), "MISSILE SELECT")){
					showMisilleSelect=true;
				}
			}	
		}
		else{
			if(GUI.Button(new Rect (Screen.width/2-insideWidth/2 , 30 , insideWidth, 40), "CANCEL MISILLE")){
				cancelMissile();
			}
			//GUI.Window(1, new Rect (Screen.width/2-width/2 , 10 , width, height+10), this.cancelMenu, "");
		}
	}
	
	public void missileSelectMenu(int id){
		GUILayout.BeginVertical();
		GUILayout.Space(10);
		
		if(GUILayout.Button("MISSILE SELECT")){
			showMisilleSelect=false;
		}
		
		GUILayout.BeginHorizontal();
		foreach( GameObject missile in missiles){
			if (GUILayout.Button( missile.GetComponent<Missile>().texture, "PicButton")){
				player.GetComponent<Ship>().selectMissile(missile);
			}
		}
		GUILayout.EndHorizontal();
		GUILayout.EndVertical();
	}
	
	public void misilleWarning(int id){
		GUILayout.BeginVertical();
		GUILayout.Space(10);
		
		GUILayout.Label("Missile didnt selected", "Warning");
		GUILayout.EndVertical();
	}
	
	public static void hitTarget(){
		gm.player.GetComponent<Ship>().hit++;
		
		if (gm.player.GetComponent<Ship>().hit == gm.maxHit){
			won();
		}
	}
	
	public static void won(){
		Application.LoadLevel("won");
	}
	
	public static void playerFired(GameObject missile){
		gm.playerTurn=false;
		
		if(gm.lastTrail!=null){
			Destroy(gm.lastTrail);
		}
		
		GameObject m= Instantiate(gm.trail, missile.transform.position, missile.transform.rotation) as GameObject;
		m.transform.parent= missile.transform;
		if(m.GetComponent<TrailRenderer>())
			m.GetComponent<TrailRenderer>().time=gm.trailTimeOut;
		
		gm.lastTrail=m;
		
		gm.currentMissile= missile;
	}
	
	public static void missileOver(GameObject missile){
		gm.playerTurn=true;
		
		/* release trail */
		string trailName="Trail v2(Clone)";
		if(missile.transform.Find(trailName)){
			GameObject m= missile.transform.Find(trailName).gameObject;
			m.transform.parent= gm.gameObject.transform;
			
		}
		
		gm.camera.setZoom(gm.fixedZoom);
	}
	
	public static void cancelMissile(){
		gm.currentMissile.GetComponent<Missile>().explode(new Vector3(0,0,0));
	}
	
	/* Dragging functions */
	public static void startDrag(Vector3 pos){
		gm.selectCircle.transform.position=pos;
		gm.selectCircle.active=true;
		
		Vector3 p= Helper.getMousePos()-pos;
		if(p.magnitude > Ship.maxFireLength){
			p= p.normalized * Ship.maxFireLength;
		}
		drawFireLine(pos+p);
	}
	
	public static void exitDrag(){
		gm.selectCircle.active=false;
		hideLine();
	}
	
	public static void drawFireLine(Vector3 end){
		GameObject line= gm.selectCircle.transform.Find("Line").gameObject;
		
		Vector3 start=gm.selectCircle.transform.position;
		start.y=end.y;
		start+= (end - start).normalized*1.5f;
		
		
		line.active=true;
		line.GetComponent<LineRenderer>().SetPosition(0, start);
		line.GetComponent<LineRenderer>().SetPosition(1, end);
	}
	
	public static void hideLine(){
		GameObject line= gm.selectCircle.transform.Find("Line").gameObject;
		line.active=false;
	}
	/* End of dragging functions */
}
