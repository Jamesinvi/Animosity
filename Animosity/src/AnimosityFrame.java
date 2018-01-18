import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;

public class AnimosityFrame extends JFrame {
	ArrayList<Creature>creatures=new ArrayList<Creature>();
	static String name;
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
		this.B_stop.setEnabled(false);
		this.B_resume.setEnabled(false);
		this.add(mainPanel);
		this.setVisible(true);
		simulation.start();
	}
}
