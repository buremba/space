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
		int height=250;
		GUI.Window(1, new Rect (Screen.width/2-width/2 , Screen.height/2-height/2, width, height), this.mainMenu, "");
	}
	
	public void mainMenu(int id){
		GUILayout.BeginVertical();
		GUILayout.Space(10);
		
		GUILayout.Label("Main Menu");
		
		if (GUILayout.Button("Start")){
			Application.LoadLevel("main");
		}
		
		if (GUILayout.Button("Exit")){
			Application.Quit();
		}

		GUILayout.EndVertical();
	}
}
