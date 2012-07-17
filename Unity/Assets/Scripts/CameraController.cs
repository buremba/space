using UnityEngine;
using System.Collections;

public class CameraController : MonoBehaviour {
	public float speed=60;
	public float minHeight=25;
	public float maxHeight=100;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		transform.Translate(0, 0, Input.GetAxis("Mouse ScrollWheel")*speed);
		
		fixHeight();
	}
	
	private void fixHeight(){
		if(transform.position.y<minHeight){
			Vector3 v= transform.position;
			v.y=minHeight;
			transform.position=v;
		}
		else if(transform.position.y>maxHeight){
			Vector3 v= transform.position;
			v.y=maxHeight;
			transform.position=v;
		}
	}
	
	public void lookAt(Vector3 pos){
		lookAt(pos, 0);
	}
	
	public void lookAt(Vector3 pos, float zoom){
		transform.position=pos;
		setZoom(zoom);
		
		fixHeight();
	}
	
	public void setZoom(float zoom){
		Vector3 v= transform.position;
		v.y=zoom;
		transform.position=v;
		
		fixHeight();
	}
}
