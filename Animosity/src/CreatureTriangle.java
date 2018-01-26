
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class CreatureTriangle extends Creature{

	
	CreatureTriangle(Simulation world,float posX, float posY, int radius){
		DNA[0]=Utilities.randFloat(-1, 1);
		DNA[1]=Utilities.randFloat(1, 2);
		this.world=world;
		this.width=5;
		this.height=5;
		this.location=new Vector(posX,posY);
		this.velocity=new Vector(0,0);
		this.acceleration=new Vector(0,0);
		this.maxforce=0.2f;
		this.maxspeed=2f;
		this.radius=radius;
		this.lifetime=800;
		this.reproductionDelta=180;
		this.adulthood=600;
		this.health=200;
		this.perceptionRadius=500;
	}

	public void move(){
		if(lifetime<adulthood && reproductionDelta<=0) {
			reproduce();
		}
		applyBehaviours();
		update();
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		location.add(velocity);
		acceleration.mult(0);
	}
	private void reproduce() {
		int rng=Utilities.random.nextInt(100);
		if(rng>90) {
			Creature child=world.generateCreatureTriangle((int)this.getLocationX(),(int)this.getLocationY());
			if(rng>95) {
				child.mutate();
			}
			reproductionDelta=250;
		}
	}
	public Vector seek(Vector target){
		Vector desired=Vector.sub(target,location);
		distance=desired.mag();
		
		if(distance<10){
			float m=map(distance,0,100,0,maxspeed);
			desired.setMag(m);
		}
		else{
			desired.setMag(maxspeed);
		}
		Vector steering=Vector.sub(desired,velocity);
		steering.limit(maxforce);
		return steering;
	}
	Vector separate(List<? extends Creature>creatures){
		float desiredSep=radius*2;
		Vector sum=new Vector(0,0);
		int count=0;
		// For every creature in the system, check if it's too close
		for (int i=creatures.size()-1;i>=0;i--){
			if(creatures.get(i) instanceof CreatureTriangle) {
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
	public void applyBehaviours(){
		Vector sepForceTriangles=separate(world.trianglelist);
		Vector seekForcePoints=seek(eat(world.creaturelist));
		sepForceTriangles.mult(DNA[1]);
		seekForcePoints.mult(DNA[0]);
		applyForce(sepForceTriangles);
		applyForce(seekForcePoints);
	}
	Vector eat(List<Creature>list) {
		Vector res=new Vector(this.velocity);
		double max= perceptionRadius;
		Creature closest=null;
		for (int i=0;i<list.size();i++) {
			if(list.get(i) instanceof CreaturePoint) {
				float dist=Vector.dist(this.location, list.get(i).location);
					if (dist<max) {
						max=dist;
						closest=list.get(i);
					}
			}
		}
		try {
			if (Vector.dist(this.location, closest.location)<closest.radius && closest instanceof CreaturePoint) {
				closest.die();
				health+=150;
			}
			res=closest.location;
			this.targetCreature=closest;
		}catch (NullPointerException e) {
		}
		return res;
	}
	@Override
	public void die() {
		world.creaturelist.remove(this);
		world.trianglelist.remove(this);
	}
	@Override
	public void mutate() {
		this.DNA[0]=this.DNA[0]+Utilities.randFloat(-0.1f, 0.1f);
		this.DNA[1]=this.DNA[1]+Utilities.randFloat(-0.1f, 0.1f);
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
		return "CreatureTriangle";
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

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	void display(Graphics2D g2) {
		TriangleShape triangle=new TriangleShape(this.getWidth(),this.getHeight(),Color.BLUE);
		int triangleX=(int)(this.getLocationX())-this.getWidth()/2;
		int triangleY=(int)(this.getLocationY())+this.getHeight()/2;
		Vector trianglePos=new Vector(triangleX,triangleY);
		triangle.drawMe(g2, trianglePos);
		if(world.debugging) {
			drawLineToTarget(g2);
			Ellipse2D perception=new Ellipse2D.Double(this.location.x-this.perceptionRadius/2,this.location.y-this.perceptionRadius/2,this.perceptionRadius,this.perceptionRadius);
			g2.draw(perception);
		}
	}

	@Override
	void drawLineToTarget(Graphics2D g2) {
		try {
			Color red=new Color(1.0f,0f,0f,0.2f);
			drawDNALines(g2);
			g2.setColor(red);
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
		Line2D line=new Line2D.Float(this.getLocationX(), this.getLocationY(), this.getLocationX()+this.velocity.x*DNA[0]*30,this.getLocationY()+this.velocity.y*DNA[0]*30);
		Line2D line2=new Line2D.Float(this.getLocationX(), this.getLocationY(), this.getLocationX()-this.velocity.x*DNA[1]*30,this.getLocationY()-this.velocity.y*DNA[1]*30);
		g2.setColor(Color.CYAN);
		g2.draw(line);
		g2.setColor(Color.MAGENTA);
		g2.draw(line2);
		
	}
	
}
