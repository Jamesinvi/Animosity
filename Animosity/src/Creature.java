import java.util.ArrayList;

public abstract class Creature {
	
	long ID=0;
	Simulation world;
	Creature targetCreature;
	//CREATURE PARAMETERS
	float maxforce;
	float maxspeed;
	float distance;
	int width;
	int height;
	int lifetime;
	int adulthood;
	int reproductionDelta;
	boolean reproductionBoolean;
	int health;
	
	//Position and Speed of the ball
	Vector location;
	Vector velocity;
	Vector acceleration;
	
	//CREATURE RADIUS
	int radius;
	
	
	abstract void move();
	abstract void applyBehaviours(ArrayList<Creature> a);
	abstract int getHeight();
	abstract int getWidth();


	//GETTERS AND SETTERS + TOSTRING METHODS
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health=health;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public int getAdulthood() {
		return adulthood;
	}

	public void setAdulthood(int adulthood) {
		this.adulthood = adulthood;
	}

	public int getReproductionDelta() {
		return reproductionDelta;
	}

	public void setReproductionDelta(int reproductionDelta) {
		this.reproductionDelta = reproductionDelta;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
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

