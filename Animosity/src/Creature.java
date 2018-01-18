import java.util.ArrayList;

public abstract class Creature {

	String type;
	Simulation world;
	
	//CREATURE PARAMETERS
	float maxforce;
	float maxspeed;
	float distance;
	int lifetime;
	
	//Position and Speed of the ball
	Vector location;
	Vector velocity;
	Vector acceleration;
	
	//CREATURE RADIUS
	int radius;
	
	static float map(float value, float start1, float stop1, float start2, float stop2){
		//value:stop1-start1=x:stop2-start1
		float t1=stop1-start1;
		float t2=stop2-start2;
		float res=value*(t2/t1);
		//System.out.println("Value: "+value+" Start1: "+start1+" Stop1: "+stop1+" Start2: "+start1+" Stop2: "+stop2);
		//System.out.println("t1: "+t1+" t2: "+t2+"res: "+res);
		return res;
	}
	
	//GETTERS AND SETTERS + TOSTRING METHODS
	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	abstract public void move();
	abstract public Vector seek(Vector target);
	abstract Vector separate(ArrayList<Creature>creatures);
	abstract public void applyBehaviours(ArrayList<Creature>creatures);
	abstract public int getWidth();
	abstract public int getHeight();
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Vector getLocation() {
		return location;
	}
	public void setLocation(Vector location) {
		this.location = location;
	}
	public Vector getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	public float getLocationX(){
		return location.getX();
	}
	public float getLocationY(){
		return location.getY();
	}
	public void applyForce(Vector f){
		acceleration.add(f);
	}
	@Override
	public String toString() {
		return "CreaturePoint [world=" + world + ", maxforce=" + maxforce + ", maxspeed=" + maxspeed + ", distance="
				+ distance + ", location=" + location + ", velocity=" + velocity + ", acceleration=" + acceleration
				+ ", radius=" + radius + "]";
	}
	public void debugString(){
		System.out.print(this.getClass()+" ");
	}
	
}

