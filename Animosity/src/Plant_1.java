import java.awt.Color;
import java.awt.Graphics2D;

public class Plant_1 extends Creature{
	Plant_1(Simulation world,float posX, float posY, int radius){
		this.world=world;
		this.width=radius;
		this.height=radius*2;
		this.radius=radius;
		this.location=new Vector(posX,posY);
		this.radius=radius;
		this.lifetime=10000;
		this.reproductionDelta=600;
		this.adulthood=9999;
		this.health=lifetime;
		this.targetCreature=null;
	}

	@Override
	void move() {
		if(lifetime<adulthood && reproductionDelta<=0) {reproduce();}
		update();
	}
	private void reproduce() {
		int rng=Utilities.random.nextInt(100);
		if(rng>95) {
			world.generatePlant_1(Utilities.RNGLocX(),Utilities.RNGLocY());
			reproductionDelta=500;
		}
		
	}
	@Override
	public void die() {
		world.creaturelist.remove(this);
		world.plantlist.remove(this);
	}

	@Override
	void applyBehaviours() {
	}

	int getHeight() {
		return height;
	}


	int getWidth() {
		return width;
	}

	@Override
	void display(Graphics2D g2) {

        g2.setColor(Utilities.my_Brown);
        g2.fillRect((int)this.location.x-this.width/2, (int)this.location.y-this.height/2, width-2, height);

        g2.setColor(Utilities.my_green);
        g2.fillOval((int)this.location.x-this.radius/2-1, (int)this.location.y-this.radius, this.radius, this.radius);
        g2.fillOval((int)this.location.x-this.radius/2-1, (int)this.location.y-this.radius-4, this.radius, this.radius);
        g2.fillOval((int)this.location.x-this.radius/2+3-1, (int)this.location.y-this.radius, this.radius, this.radius);
        g2.fillOval((int)this.location.x-this.radius/2-3-1, (int)this.location.y-this.radius, this.radius, this.radius);
		g2.setColor(Color.white);
	}

	@Override
	void drawLineToTarget(Graphics2D g2) {
		return;
		
	}
	@Override 
	public String toString() {
		return "Plant_1";
	}

	@Override
	void drawDNALines(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
}
