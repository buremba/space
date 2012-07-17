using UnityEngine;
using System.Collections;

public class Missile : MonoBehaviour {
	public float speed=1;
	public GameObject explosion;
	public Texture texture; /* thumbail texture */

	void Start () {
	
	}
	

	void Update () {
	
	}
	
	/* direction and scale */
	public void fire(Vector3 vec, float scale){
		Vector3 v= vec.normalized*speed*scale;
		rigidbody.velocity=v;
	}
	
	public void explode(Vector3 normal){
		Instantiate(explosion, transform.position, Quaternion.LookRotation(normal*-1)) ;
		
		GameManager.missileOver(gameObject);
		
		Destroy(gameObject);
	}
}
