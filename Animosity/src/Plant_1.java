import java.util.ArrayList;

public class Plant_1 extends Creature{
	Plant_1(Simulation world,float posX, float posY, int radius){
		this.world=world;
		this.width=15;
		this.height=15;
		this.radius=radius;
		this.location=new Vector(posX,posY);
		this.radius=radius;
		this.lifetime=10000;
		this.reproductionDelta=40;
		this.adulthood=9999;
		this.health=lifetime;
	}

	@Override
	void move() {
		if(lifetime<adulthood && reproductionDelta<=0) {reproduce();}
	}
	private void reproduce() {
		int rng=Utilities.RNGLocX();
		if(rng>90) {
			world.frm.generatePlant_1(Utilities.RNGLocX(),Utilities.RNGLocY());
			reproductionDelta=300;
		}
		
	}

	@Override
	void applyBehaviours(ArrayList<Creature> a) {
	}

	@Override
	int getHeight() {
		return height;
	}

	@Override
	int getWidth() {
		return width;
	}
}
