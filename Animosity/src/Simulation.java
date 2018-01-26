import java.awt.Canvas;
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
	Point mousePoint;
	public final static int WIDTH=1840;
	public final static int HEIGHT=500;
	//Runtime checks and counters
	public boolean debugging=false;
	public boolean running=false;
	public int tickCount=0;
	public int delta=0;
	//Creature and Plant arrayList
	List<Creature>creaturelist=new ArrayList<Creature>();
	List<Plant_1>plantlist=new ArrayList<Plant_1>();
	List<CreatureTriangle>trianglelist=new ArrayList<CreatureTriangle>();
	List<CreaturePoint> pointlist=new ArrayList<CreaturePoint>();
	private boolean paused=false;
	private int frameCount=0;
	private int fps=60;
	private float interpolation=0;

	
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
			generatePlant_1(Utilities.RNGLocX(), Utilities.RNGLocY());
			generateCreaturePoint(Utilities.RNGLocX(), Utilities.RNGLocY());
			//generateCreatureTriangle(Utilities.RNGLocX(), Utilities.RNGLocY());
		}
	}
	public void run() {
		  //This value would probably be stored elsewhere.
	      final double GAME_HERTZ = 60.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      //If you're worried about visual hitches more than perfect timing, set this to 1.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //Simple way of finding FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (running)
	      {
	         double now = System.nanoTime();
	         int updateCount = 0;
	         
	         if (!paused)
	         {
	             //Do as many game updates as we need to, potentially playing catchup.
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
	            {
	               tick();
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	   
	            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	         
	            //Render. To do so, we need to calculate interpolation for a smooth render.
	            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
	            drawSim(interpolation);
	            lastRenderTime = now;
	         
	            //Update the frames we got.
	            int thisSecond = (int) (lastUpdateTime / 1000000000);
	            if (thisSecond > lastSecondTime)
	            {
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	            
	               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
	               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
	               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	}
	private void drawSim(float interpolation) {
	  this.setInterpolation(interpolation);
	  this.repaint();
	}
    public void setInterpolation(float interp) {
       interpolation = interp;
    }
	   
	void updatelists(){
		for (Creature creature:creaturelist) {
			if (creature instanceof Plant_1 && !plantlist.contains(creature)) {
				plantlist.add((Plant_1)creature);
			}
			else if(creature instanceof CreatureTriangle && !trianglelist.contains(creature)) {
				trianglelist.add((CreatureTriangle)creature);
			}
			else if (creature instanceof CreaturePoint && !pointlist.contains(creature)) {
				pointlist.add((CreaturePoint)creature);
			}
		}
	}
	
	//TICK---MAIN LOGIC OF THE SIMULATION
	private void tick() {
		updatelists();
		if (creaturelist.size()>0){
			for (int i=0;i<creaturelist.size();i++){
				creaturelist.get(i).move();
			}
		}
		//Only send data once in a while to avoid overloading the system
		if (delta>100){
			int creaturePointCount=pointlist.size();
			int creatureTriangleCount=trianglelist.size();
			int creaturePlantCount=plantlist.size();

			//Send data to charts and console
			frm.northPanel.updateXYDataset(creaturelist.size());
			frm.northPanel.updateXYAreasDataset(creaturePointCount,creatureTriangleCount,creaturePlantCount);
			delta=0;
		}
		tickCount++;;
		delta++;
	}
	//CREATURE GENERATOR METHODS
	Creature generateCreaturePoint(int x,int y){
		Creature res=new CreaturePoint(this,x,y,4);
		if (creaturelist.size()==5000) makeSpace();
		creaturelist.add(res);
		return res;
	}
	Creature generateCreatureTriangle(int x,int y){
		Creature res=new CreatureTriangle(this,x,y,4);
		if (creaturelist.size()==5000) makeSpace();
		creaturelist.add(new CreatureTriangle(this,x,y,4));
		return res;
	}
	void generatePlant_1(int x,int y){
		if (creaturelist.size()==5000) {
			makeSpace();
		}
		creaturelist.add(new Plant_1(this,x,y,5));
	}
	void makeSpace() {
		for (int i=0;i<300;i++) {
			plantlist.get(i).die();
		}
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

