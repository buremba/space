using UnityEngine;
using System.Collections;

public class Helper {
	public static Vector3 getMousePos(){
		RaycastHit hitInfo;
		
		Physics.Raycast(Camera.mainCamera.ScreenPointToRay(Input.mousePosition),out hitInfo, Mathf.Infinity,1 << 8 /* background */);
		
		if(hitInfo.collider!=null && hitInfo.collider.gameObject.name=="Background"){
			return hitInfo.point;
		}
		else{
			return new Vector3(0,0,0);
		}
	}
}
