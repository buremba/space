using UnityEngine;
using System.Collections;

public class Ship : MonoBehaviour {
	public bool ai=false;
	public int hit=0;
	
	public GameObject missile; /* last choosed missile type */
	
	public static float maxFireLength= 10;
	
	/* for fixing android touch */
	private bool drag=false;
	
	void Start () {
	
	}
	
	void Update () {		
		/* for fixing android touch */
		if( Input.touchCount > 0 && 
			Input.GetTouch(0).phase==TouchPhase.Began && 
			isMouseOver())
				drag=true;
		
		if( Input.touchCount > 0 && 
			Input.GetTouch(0).phase==TouchPhase.Ended){
			
			drag=false;
			OnMouseUp();
		}
		
		if(drag) OnMouseDrag();
		
	}
	
	public void turnTo(Vector3 pos){
		pos.y= transform.position.y;
		gameObject.transform.LookAt(pos);
	}
	
	public void fire(Vector3 pos){
		if(missile == null){
			print("Missile didnt selected!!");
			return;
		}
		
		float h= (pos-transform.position).magnitude;
		if(h>maxFireLength)h=maxFireLength;
		
		else if(h<0){
			print("Dafuq? Blow zero?");
			return;
		}
		
		GameObject m= Instantiate(missile, transform.position, transform.rotation) as GameObject;
		m.GetComponent<Missile>().fire( pos - transform.position, h/maxFireLength );
		
		GameManager.playerFired(m);
	}
	
	/* called when user clicked missile select menu */
	public void selectMissile(GameObject missile){
		this.missile= missile;
	}
	
	private bool isMouseOver(){
		RaycastHit hitInfo;
		Ray ray = Camera.main.ScreenPointToRay (Input.mousePosition);
		if(collider.Raycast(ray, out hitInfo, Mathf.Infinity)){
			return true;
		}
		return false;
	}
	
	void OnMouseDrag() {
		if(!ai && GameManager.gm.playerTurn){
			GameManager.startDrag(transform.position);
			turnTo( Helper.getMousePos() );
		}
	}
	
	void OnMouseUp(){
		if(!ai && GameManager.gm.playerTurn){
			GameManager.exitDrag();
			fire( Helper.getMousePos() );
		}
	}
}
