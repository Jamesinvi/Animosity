import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class CreaturePoint extends Creature {
	
	CreaturePoint(Simulation world,float posX, float posY, int radius){
		this.world=world;
		this.radius=radius;
		this.location=new Vector(posX,posY);
		this.velocity=new Vector(0,0);
		this.acceleration=new Vector(0,0);
		this.maxforce=0.1f;
		this.maxspeed=1.9f;
		this.lifetime=1000;
		this.reproductionDelta=150;
		this.adulthood=800;
		this.health=250;
		this.perceptionRadius=500;
	}

	public void move(){
		if(lifetime<adulthood && reproductionDelta<=0) {
			if(world.frm.creatures.size()>=500){
				world.frm.makeSpace();
			}
			reproduce();
			}
		applyBehaviours(world.creaturelist);
		update();
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		location.add(velocity);
		acceleration.mult(0);
	}
	private void reproduce() {
		int rng=Utilities.RNGLocX();
		if(rng>1500) {
			world.frm.generateCreaturePoint((int)this.getLocationX(),(int)this.getLocationY());
			reproductionDelta=100;
		}
	}

	public Vector seek(Vector target){
		Vector desired=Vector.sub(target,location);
		distance=desired.mag();
		if(distance<30){
			float m=map(distance,0,30,0,maxspeed);
			desired.setMag(m+5);
		}
		else{
			desired.setMag(maxspeed);
		}
		Vector steering=Vector.sub(desired,velocity);
		steering.limit(maxforce);
		return steering;
	}
	Vector separate(ArrayList<Creature>creatures){
		float desiredSep=radius;
		Vector sum=new Vector(0,0);
		int count=0;
		// For every creature in the system, check if it's too close
		for (int i=creatures.size()-1;i>=0;i--){
			if(creatures.get(i) instanceof CreaturePoint) {
				float dist=Vector.dist(location,creatures.get(i).getLocation());
				// If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
				if((dist>0)&&(dist<desiredSep)){
					// Calculate vector pointing away from neighbor
					Vector diff=Vector.sub(location, creatures.get(i).getLocation());
					diff.normalize();
					diff.div(dist);			// Weight by distance
					sum.add(diff);
					count++;				//keep track of the number of creatures
				}
			}
		}
		// Average -- divide by how many
		if (count>0){
			sum.div(count);
			sum.normalize();
			sum.mult(maxspeed);
			// Implement Reynolds: Steering = Desired - Velocity
			sum.sub(velocity);
			sum.limit(maxforce);
		}
		return sum;
		
	}
	public void applyBehaviours(ArrayList<Creature>creatures){
		Vector separationForce=separate(creatures);
		Vector seekForce=seek(eat(world.plantlist));
		separationForce.mult(1.5f);
		seekForce.mult(1);
		applyForce(separationForce);
		applyForce(seekForce);
	}
	Vector eat(ArrayList<Creature>list) {
		Vector res=new Vector(this.velocity);
		double max= perceptionRadius;
		Creature closest=null;
		for (int i=0;i<list.size();i++) {
			float dist=Vector.dist(this.location, list.get(i).location);
			if (dist<max) {
				max=dist;
				closest=list.get(i);
			}
		}
		try {
			if (Vector.dist(this.location, closest.location)<closest.radius && closest instanceof Plant_1) {
				list.remove(closest);
				world.creaturelist.remove(closest);
				health+=150;
			}
			res=closest.location;
			this.targetCreature=closest;
		}catch (NullPointerException e) {
			closest=null;
		}
		return res;
	}
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


	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	@Override
	public String toString() {
		return "CreaturePoint [world=" + world + ", maxforce=" + maxforce + ", maxspeed=" + maxspeed + ", lifetime="
				+ lifetime + ", distance=" + distance + ", location=" + location + ", velocity=" + velocity
				+ ", acceleration=" + acceleration + ", radius=" + radius + "]";
	}
	

	static float map(float value, float start1, float stop1, float start2, float stop2){
		//value:stop1-start1=x:stop2-start1
		float t1=stop1-start1;
		float t2=stop2-start2;
		float res=value*(t2/t1);
		//System.out.println("Value: "+value+" Start1: "+start1+" Stop1: "+stop1+" Start2: "+start1+" Stop2: "+stop2);
		//System.out.println("t1: "+t1+" t2: "+t2+"res: "+res);
		return res;
	}

	@Override
	int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void display(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		Ellipse2D circle=new Ellipse2D.Double(this.location.x-this.radius/2,this.location.y-this.radius/2,this.radius,this.radius);
		g2.fill(circle);
		if(world.debugging) {
			drawLineToTarget(g2);
			Ellipse2D perception=new Ellipse2D.Double(this.location.x-this.perceptionRadius/2,this.location.y-this.perceptionRadius/2,this.perceptionRadius,this.perceptionRadius);
			g2.draw(perception);
		}
	}

	@Override
	void drawLineToTarget(Graphics2D g2) {
		try {
			Color red=new Color(1.0f,1.0f,1.0f,0.2f);
			g2.setColor(red);
			Creature target=this.targetCreature;
			g2.draw(new Line2D.Float(this.getLocationX(),
									 this.getLocationY(),
									 target.getLocationX(),target.getLocationY()));
			}catch (NullPointerException  | IndexOutOfBoundsException e) {
			return;
		}
	}
}
