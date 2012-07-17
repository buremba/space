using UnityEngine;
using System.Collections;

public class Target : MonoBehaviour {
	void Start () {
	
	}
	
	void Update () {
	
	}
	

	void OnCollisionEnter(Collision collision) {
        foreach (ContactPoint contact in collision.contacts) {
			if(contact.otherCollider.gameObject.GetComponent<Missile>() != null){
				contact.otherCollider.gameObject.GetComponent<Missile>().explode(contact.normal);
				
				GameManager.hitTarget();
				
				Destroy(gameObject);
			}
        }
    }
}
