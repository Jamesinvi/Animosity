import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Creature {
	
	long ID=0;
	Simulation world;
	Creature targetCreature;
	//CREATURE PARAMETERS
	float [] DNA=new float[5];
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
	float perceptionRadius;
	
	//Position and Speed of the ball
	Vector location;
	Vector velocity;
	Vector acceleration;
	
	//CREATURE RADIUS
	int radius;
	
	
	abstract void move();
	abstract void applyBehaviours();
	abstract void drawLineToTarget(Graphics2D g2);
	abstract void drawDNALines(Graphics2D g2);
	abstract void display(Graphics2D g2);
	abstract void die();

	public void update(){
		if(this.lifetime>0 && this.health>0){
			this.setLifetime(this.lifetime-1);
			this.setReproductionDelta(this.reproductionDelta-1);
			this.setHealth(this.health-1);
		}else {
			die();
			world.creaturelist.remove(this);
		}
	}
	void mutate() {
		
	}
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
	public void debugString(){
		System.out.print(this.getClass()+" ");
	}
	
}

