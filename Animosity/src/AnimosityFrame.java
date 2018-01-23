import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;

public class AnimosityFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4091715683579074343L;
	ArrayList<Creature>creatures=new ArrayList<Creature>();
	String name;
	static double version=0.1;
	Simulation simulation=new Simulation(this);
	JPanel mainPanel=new JPanel(new BorderLayout());
	GraphPanel northPanel=new GraphPanel(this,new FlowLayout());
	JPanel centerPanel=new JPanel(new FlowLayout());
	JPanel southPanel=new JPanel (new FlowLayout());
	
	JButton B_generate_creature=new JButton("Generate Creature");
	JButton B_generate_predator=new JButton("Generate Predator");
	JButton B_stop=new JButton("Stop");
	JButton B_resume=new JButton("Start/Resume");
	JButton B_savePNG=new JButton("Save Graphs as PNG");
	
	JCheckBox debugCheckBox=new JCheckBox("Debug visualization");
	AnimosityListener listener=new AnimosityListener(this);
	
	
	AnimosityFrame(String n){
		name=n;
		this.setTitle(name+version);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		centerPanel.add(simulation);
		southPanel.add(B_generate_creature);
		southPanel.add(B_generate_predator);
		southPanel.add(B_stop);
		southPanel.add(B_resume);
		southPanel.add(B_savePNG);
		southPanel.add(debugCheckBox);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.B_generate_creature.addActionListener(listener);
		this.B_generate_creature.setActionCommand("GENERATE");
		this.B_generate_predator.addActionListener(listener);
		this.B_generate_predator.setActionCommand("GENERATEPREDATOR");
		this.B_stop.addActionListener(listener);
		this.B_stop.setActionCommand("STOP");
		this.B_resume.addActionListener(listener);
		this.B_resume.setActionCommand("RESUME");
		this.B_savePNG.addActionListener(listener);
		this.B_savePNG.setActionCommand("SAVEGRAPHS");
		this.debugCheckBox.addActionListener(listener);
		this.B_stop.setEnabled(false);
		this.B_generate_creature.setEnabled(false);
		this.B_generate_predator.setEnabled(false);
		this.B_resume.setEnabled(true);
		this.add(mainPanel);
		this.setVisible(true);
	}
	//CREATURE GENERATOR METHODS
	void generateCreaturePoint(int x,int y){
		if (creatures.size()==1000) makeSpace();
		creatures.add(new CreaturePoint(simulation,x,y,4));
	}
	void generateCreatureTriangle(int x,int y){
		if (creatures.size()==1000) makeSpace();
		creatures.add(new CreatureTriangle(simulation,x,y,4));
	}
	void generatePlant_1(int x,int y){
		if (creatures.size()==1000) {
			makeSpace();
		}
		creatures.add(new Plant_1(simulation,x,y,5));
	}
	void makeSpace() {
		for (int i=0;i<300;i++) {
			creatures.remove(creatures.get(creatures.size()-1));
		}
	}
}
