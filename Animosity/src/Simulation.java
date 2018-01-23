import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Simulation extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 521807317949566808L;
	//Frame  setup
	AnimosityFrame frm;
	public final static int WIDTH=1840;
	public final static int HEIGHT=500;
	//Runtime checks and counters
	public boolean debugging=false;
	public boolean running=false;
	public int tickCount=0;
	public int delta=0;
	//Creature and Plant arrayList
	ArrayList<Creature>creaturelist=new ArrayList<Creature>();
	ArrayList<Creature>plantlist=new ArrayList<Creature>();
	ArrayList<Creature>trianglelist=new ArrayList<Creature>();

	
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
		for(int i=0;i<20;i++) {
			frm.generatePlant_1(Utilities.RNGLocX(), Utilities.RNGLocY());
			frm.generateCreaturePoint(Utilities.RNGLocX(), Utilities.RNGLocY());
			frm.generateCreatureTriangle(Utilities.RNGLocX(), Utilities.RNGLocY());
		}
	}
	public void run() {
		long lastTime = System.nanoTime();
	    final double ns = 1000000000.0 / 60.0;              // Here you can change the rate
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    int frames = 0;
	    int updates = 0;
	    while (running) {
	    	long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        while (delta >= 1) {
	           tick();
	           delta--;
	           updates++;   
		       repaint();
		       frames++;			// Anything put inside this while loop will be executed 60 times per second
	        }       
	        						// Anything put outside is executed as many times it can --add repaint() method here later
	        if (System.currentTimeMillis() - timer > 1000) {
	           timer += 1000;
	           frm.setTitle(frm.name+ "--"+updates + " ups, " + frames + " fps");
	           updates = 0;
	           frames = 0;
	        }
	   }
	      
	}
	//TICK---MAIN LOGIC OF THE SIMULATION
	private void tick() {
		creaturelist=frm.creatures;
		if (creaturelist.size()>0){
			for (int i=0;i<creaturelist.size();i++){
				Creature creatureI=creaturelist.get(i);
				creatureI.update();
			}
		}
		//Only send data once in a while to avoid overloading the system
		if (delta>100){
			int creaturePointCount=0;
			int creatureTriangleCount=0;
			int creaturePlantCount=0;
			//Count Creatures and types
			for (int i=0;i<creaturelist.size();i++){
				if (creaturelist.get(i) instanceof CreaturePoint){
					creaturePointCount++;
				}else if(creaturelist.get(i) instanceof CreatureTriangle){
					creatureTriangleCount++;
				}else if(creaturelist.get(i) instanceof Plant_1) {
					creaturePlantCount++;
				}
			}
			creaturePlantCount++;
			//Send data to charts and console
			frm.northPanel.updateXYDataset(creaturelist.size());
			frm.northPanel.updateXYAreasDataset(creaturePointCount,creatureTriangleCount,creaturePlantCount);
			delta=0;
		}
		tickCount++;;
		delta++;
	}
	//ALL PAINTING HAPPENS HERE 	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		for (int i=0;i<creaturelist.size();i++){
			creaturelist.get(i).display(g2);
			g2.setColor(Color.LIGHT_GRAY);
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

