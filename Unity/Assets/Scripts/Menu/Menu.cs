using UnityEngine;
using System.Collections;

public class Menu : MonoBehaviour {
	public GUISkin skin;
	
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	void OnGUI(){
		GUI.skin=skin;
		
		int width= 400;
		int height=240;
		GUI.Window(1, new Rect (Screen.width/2-width/2 , Screen.height/2-height/2, width, height), this.mainMenu, "");
	}
	
	public void mainMenu(int id){
		GUILayout.BeginVertical();
		
		GUILayout.Space(10);
		GUILayout.Label("MAIN MENU");
		
		
		GUILayout.BeginHorizontal();
		
		if (GUILayout.Button("NEW GAME")){
			Application.LoadLevel("main");
		}
		
		if (GUILayout.Button("LOAD")){
			//
		}

		GUILayout.EndHorizontal();
		
		if (GUILayout.Button("QUIT")){
			Application.Quit();
		}
		
		if (GUILayout.Button("ABOUT")){
			Application.Quit();
		}
		
		GUILayout.EndVertical();
	}
}
