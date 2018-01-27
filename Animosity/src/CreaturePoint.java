import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.List;

public class CreaturePoint extends Creature {
	static float maxspeedVal=1.9f;
	static float maxforceVal=0.2f;
	static int lifetimeVal=1000;
	static int repdeltaVal=260;
	static int adulthoodVal=800;
	static int healthVal=350;
	static int perceptionRadVal=300;
	
	CreaturePoint(Simulation world,float posX, float posY, int radius){
		DNA[0]=Utilities.randFloat(-1, 1);
		DNA[1]=Utilities.randFloat(1, 2);
		DNA[2]=Utilities.randFloat(-1, 1);
		this.world=world;
		this.radius=radius;
		this.location=new Vector(posX,posY);
		this.velocity=new Vector(0,0);
		this.acceleration=new Vector(0,0);
		this.maxforce=maxforceVal;
		this.maxspeed=maxspeedVal;
		this.lifetime=lifetimeVal;
		this.reproductionDelta=repdeltaVal;
		this.adulthood=adulthoodVal;
		this.health=healthVal;
		this.perceptionRadius=perceptionRadVal;
	}

	public void move(){
		update();
		if(lifetime<adulthood && reproductionDelta<=0) {
			reproduce();
		}
		applyBehaviours();
		edges();
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		location.add(velocity);
		acceleration.mult(0);
	}
	private void reproduce() {
		int rng=Utilities.random.nextInt(100);
		if(rng>95) {
			Creature child=world.generateCreaturePoint((int)this.getLocationX(),(int)this.getLocationY());
			child.DNA=Arrays.copyOf(this.DNA, this.DNA.length);
			int rng2=Utilities.random.nextInt(100);
			if(rng2>96) {
				child.mutate();
			}
			reproductionDelta=260;
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
	Vector separate(List<? extends Creature>creatures){
		float desiredSep=radius*3;
		Vector sum=new Vector(0,0);
		int count=0;
		// For every creature in the system, check if it's too close
		for (int i=creatures.size()-1;i>=0;i--){
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
	public void applyBehaviours(){
		Vector sepForcePoints=separate(world.pointlist);
		Vector fleeForceTriangle=avoid(world.trianglelist);
		Vector seekForcePlants=eat(world.plantlist);
		seekForcePlants.mult(DNA[0]);
		sepForcePoints.mult(DNA[1]);
		fleeForceTriangle.mult(DNA[2]);
		applyForce(sepForcePoints);
		applyForce(fleeForceTriangle);
		applyForce(seekForcePlants);
	}
	Vector avoid(List<? extends Creature>list) {
		Vector res=new Vector(0,0);
		double max=perceptionRadius;
		Creature closest=null;
		for (int i=0;i<list.size();i++) {
			float dist=Vector.dist(this.location, list.get(i).location);
			if (dist<max) {
				max=dist;
				closest=list.get(i);
			}
		}
		if (closest!=null) {
			res=seek(closest.location);
			res.mult(-1);
		}
		return res;
	}
	Vector eat(List<? extends Creature>list) {
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
				closest.die();
				this.addHealth(150);
			}
			res=closest.location;
			this.targetCreature=closest;
		}catch (NullPointerException e) {
			closest=null;
		}
		return seek(res);
	}
	public void edges(){
		if (this.location.x>Simulation.WIDTH) {
			this.location.x=0;
		}
		if (this.location.x<0) {
			this.location.x=Simulation.WIDTH;
		}
		if (this.location.y>Simulation.HEIGHT) {
			this.location.y=0;
		}
		if (this.location.y<0) {
			this.location.y=Simulation.HEIGHT;
		}
	}
	@Override 
	public void mutate() {
		int number=Utilities.random.nextInt(100);
		if(number<50) {
			this.DNA[0]=this.DNA[0]+Utilities.randFloat(-0.1f, 0.1f);
		}else if(number>50) {
			this.DNA[1]=this.DNA[1]+Utilities.randFloat(-0.1f, 0.1f);
		}
	}
	@Override
	public void die() {
		world.creaturelist.remove(this);
		world.pointlist.remove(this);
		world.generatePlant_1((int)this.location.x, (int)this.location.y);

	}
	public void addHealth(int health) {
		if (this.health+health<300) {
			this.health+=health;
		}
		else if (this.health>300) {
			return;
		}else {
			this.health=300;
		}
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
		return "CreaturePoint";
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
	void display(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		Ellipse2D circle=new Ellipse2D.Double(this.location.x-this.radius/2,this.location.y-this.radius/2,this.radius,this.radius);
		g2.fill(circle);
		g2.setColor(Utilities.my_Brown);
		g2.draw(circle);
		if(world.debugging) {
			//g2.drawString(Float.toString(DNA[2]), this.location.x+radius, this.location.y+radius);
			drawDNALines(g2);
			g2.setColor(Utilities.my_black);
			drawLineToTarget(g2);
			Ellipse2D perception=new Ellipse2D.Double(this.location.x-this.perceptionRadius/2,this.location.y-this.perceptionRadius/2,this.perceptionRadius,this.perceptionRadius);
			g2.draw(perception);
		}
	}

	@Override
	void drawLineToTarget(Graphics2D g2) {
		try {
			Creature target=this.targetCreature;
			g2.draw(new Line2D.Float(this.getLocationX(),
									 this.getLocationY(),
									 target.getLocationX(),target.getLocationY()));
			}catch (NullPointerException  | IndexOutOfBoundsException e) {
			return;
		}
	}
	@Override 
	void drawDNALines(Graphics2D g2) {
		float xCoord=this.velocity.normal(velocity).x;
		float yCoord=this.velocity.normal(velocity).y;
		Line2D line=new Line2D.Float(this.getLocationX(), this.getLocationY(), this.getLocationX()+xCoord*DNA[0]*30,this.getLocationY()+yCoord*DNA[0]*30);
		Line2D line2=new Line2D.Float(this.getLocationX(), this.getLocationY(),this.getLocationX()-xCoord*DNA[1]*15,this.getLocationY()-yCoord*DNA[1]*15);
		g2.setColor(Color.CYAN);
		g2.draw(line);
		g2.setColor(Color.MAGENTA);
		g2.draw(line2);
	}
}
