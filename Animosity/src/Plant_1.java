import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Plant_1 extends Creature{
	Plant_1(Simulation world,float posX, float posY, int radius){
		this.world=world;
		this.width=5;
		this.height=5;
		this.radius=radius;
		this.location=new Vector(posX,posY);
		this.radius=radius;
		this.lifetime=10000;
		this.reproductionDelta=80;
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
		int rng=Utilities.RNGLocX();
		if(rng>1600) {
			world.frm.generatePlant_1(Utilities.RNGLocX(),Utilities.RNGLocY());
			reproductionDelta=160;
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
		g2.setColor(Color.GREEN);
		int rectX=(int)(this.getLocationX())-this.getWidth()/2;
		int rectY=(int)(this.getLocationY())-this.getHeight()/2;
		Rectangle2D rect=new Rectangle2D.Float(rectX,rectY,this.getWidth(),this.getHeight()); 
		g2.fill(rect);
		
	}

	@Override
	void drawLineToTarget(Graphics2D g2) {
		return;
		
	}
	@Override 
	public String toString() {
		return "Plant_1";
	}
}
