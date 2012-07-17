using UnityEngine;
using System.Collections;

public class Planet : MonoBehaviour {
	public float force=1;
	void Start () {
		
	}
	
	void FixedUpdate(){
		//force to all missile
		GameObject[] missiles = GameObject.FindGameObjectsWithTag("Missile");
		foreach (GameObject missile in missiles) {
			//calculate force and apply
			Vector3 f= transform.position - missile.transform.position ;
			f = (f.normalized * force) / (f.magnitude*f.magnitude);
			
			missile.rigidbody.AddForce(f);
		}
		
		//turn around
		//transform.
	}
	
	void OnCollisionEnter(Collision collision) {
        foreach (ContactPoint contact in collision.contacts) {
			
			/* if its missile, then explode it */
			if(contact.otherCollider.gameObject.GetComponent<Missile>() != null)
				contact.otherCollider.gameObject.GetComponent<Missile>().explode(contact.normal);
        }
    }
}
