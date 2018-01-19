import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Simulation extends JPanel implements Runnable {
	//Frame  setup
	AnimosityFrame frm;
	Point mousePoint;
	public final static int WIDTH=1840;
	public final static int HEIGHT=500;
	//Runtime checks and counters
	public boolean running=false;
	public int tickCount=0;
	public int delta=0;
	//Creature and Plant arrayList
	ArrayList<Creature>creaturelist=new ArrayList<Creature>();
	ArrayList<Creature>plantlist=new ArrayList<Creature>();

	
	//CONSTRUCTOR
	public Simulation(AnimosityFrame frm){
		this.frm=frm;
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.GRAY);
		this.setVisible(true);
	}
	
	
	//START AND STOP METHODS/////////////////////
	public synchronized void start(){
		new Thread(this).start();
		running=true;
		initSetup();
	}
	public synchronized void stop(){
		running=false;
	}
	
	/////////////////////////////////////////////
	public void initSetup() {
		frm.generatePlant_1(Utilities.RNGLocX(), Utilities.RNGLocY());
		frm.generateCreaturePoint(Utilities.RNGLocX(), Utilities.RNGLocY());
		frm.generateCreatureTriangle(Utilities.RNGLocX(), Utilities.RNGLocY());
		updateLists();
	}
	public void run() {
		while (running){
			tick();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TICK---MAIN LOGIC OF THE SIMULATION
	private void tick() {
		updateLists();
		if (creaturelist.size()>0){
			for (int i=0;i<creaturelist.size();i++){
				Creature creatureI=creaturelist.get(i);
				//Creature Behaviours
				mousePoint=MouseInfo.getPointerInfo().getLocation();
				creatureI.move();
				updateLists();
				creatureI.applyBehaviours(creaturelist);
				updateLists();
				if(creatureI.getLifetime()>0){
					creatureI.setLifetime(creatureI.getLifetime()-1);
					creatureI.setReproductionDelta(creatureI.getReproductionDelta()-1);
					if(creatureI.health>0) {
						creatureI.setHealth(creatureI.getHealth()-1);
					}else {
						creaturelist.remove(creatureI);
					}
					
				}
				else{
					creaturelist.remove(creatureI);
				}
			}
		}
		//Only send data once in a while to avoid overloading the system
		if (delta>100){
			int creaturePointCount=0;
			int creatureTriangleCount=0;
			int creaturePlantCount=0;
			//Count Creatures and types
			for (int i=0;i<creaturelist.size();i++){
				if (creaturelist.get(i).getClass()==CreaturePoint.class){
					creaturePointCount++;
				}else if(creaturelist.get(i).getClass()==CreatureTriangle.class){
					creatureTriangleCount++;
				}else if(creaturelist.get(i).getClass()==Plant_1.class)
					creaturePlantCount++;
			}
			//Send data to charts and console
			frm.northPanel.updateXYDataset(creaturelist.size());
			frm.northPanel.updateXYAreasDataset(creaturePointCount,creatureTriangleCount,creaturePlantCount);
			delta=0;
		}
		frm.creatures=creaturelist;
		tickCount++;;
		delta++;
		repaint();
	}
	//ALL PAINTING HAPPENS HERE 	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		for (int i=0;i<creaturelist.size();i++){
			drawAppropriateShape(g2,creaturelist.get(i));
			//drawLineToMouse(g2, i);
			g2.setColor(Color.DARK_GRAY);
		}
	}
	
	//PAINTING HELPING METHODS
	void drawLineToMouse(Graphics2D g2,int i){
		g2.setColor(Color.white);
		g2.draw(new Line2D.Float(creaturelist.get(i).getLocationX(),
								 creaturelist.get(i).getLocationY(),
								 MouseInfo.getPointerInfo().getLocation().x,
								 MouseInfo.getPointerInfo().getLocation().y));
	}
	void drawAppropriateShape(Graphics2D g2,Creature creature){
		if (creature.getClass()==CreaturePoint.class){
			g2.setColor(Color.black);
			Ellipse2D circle=new Ellipse2D.Double(creature.getLocationX()-creature.getRadius()/2,creature.getLocationY()-creature.getRadius()/2,creature.getRadius(),creature.getRadius());
			g2.fill(circle);
		}
		else if(creature.getClass()==CreatureTriangle.class){
			TriangleShape triangle=new TriangleShape(creature.getWidth(),creature.getHeight(),Color.BLACK);
			int triangleX=(int)(creature.getLocationX())-creature.getWidth()/2;
			int triangleY=(int)(creature.getLocationY())+creature.getHeight()/2;
			Vector trianglePos=new Vector(triangleX,triangleY);
			triangle.drawMe(g2, trianglePos);
		}
		else if(creature.getClass()==Plant_1.class){
			g2.setColor(Color.GREEN);
			int rectX=(int)(creature.getLocationX())-creature.getWidth()/2;
			int rectY=(int)(creature.getLocationY())-creature.getHeight()/2;
			Rectangle2D rect=new Rectangle2D.Float(rectX,rectY,creature.getWidth(),creature.getHeight()); 
			g2.fill(rect);
		}
	}
    
	void updateLists(){
		for (int i=0;i<frm.creatures.size();i++) {
			try {
				if (frm.creatures.get(i)!=creaturelist.get(i)){
					creaturelist.add(frm.creatures.get(i));
				}
			}catch (Exception e) {
				creaturelist.add(i, frm.creatures.get(i));;
				}
			try {
				if (frm.creatures.get(i).getClass()==Plant_1.class && plantlist.get(i)!=plantlist.get(i)) {
					plantlist.add(frm.creatures.get(i));
				}
			}catch (Exception e) {
				plantlist.add(frm.creatures.get(i));
			}
		}
	}
	//OTHER STATIC METHODS
	static Vector pointToPoint(Point p){
    	Vector res=new Vector(p.x,p.y);
    	return res;
    }
	static Point vectToPoint(Vector v) {
		Point res=new Point((int)v.getX(),(int)v.getY());
		return res;
	}

    static void printLists(ArrayList<Creature> a, ArrayList<Creature> b, ArrayList<Creature> c){
		System.out.print(1+": ");
		for (Creature creature: a){
			creature.debugString();
		}
		System.out.print("\n"+2+": ");
		for (Creature creature: b){
			creature.debugString();
		}
		System.out.print("\n"+3+": ");
		for (Creature creature: c){
			creature.debugString();
		}
		System.out.println(" ");
    }

}	

