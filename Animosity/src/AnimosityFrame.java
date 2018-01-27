import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class AnimosityFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4091715683579074343L;
	String name;
	static double version=1.0;
	Simulation simulation=new Simulation(this);
	JPanel mainPanel=new JPanel(new BorderLayout());
	GraphPanel graphPanel=new GraphPanel(this,new FlowLayout());
	JPanel northPanel=new JPanel(new BorderLayout());
	JPanel gridPanel=new JPanel(new GridLayout(7,4));
	JPanel subNorth=new JPanel(new BorderLayout());
	JPanel centerPanel=new JPanel(new FlowLayout());
	JPanel southPanel=new JPanel (new FlowLayout());
	
	JButton B_generate_creature=new JButton("Generate Creature");
	JButton B_generate_predator=new JButton("Generate Predator");
	JButton B_generate_plant=new JButton("Generate Plant");
	JButton B_stop=new JButton("Stop");
	JButton B_resume=new JButton("Start/Resume");
	JButton B_savePNG=new JButton("Save Graphs as PNG");
	JButton B_clearGraph=new JButton("Clear Graphs");
	JButton B_updateValues=new JButton("Update Values");
	
	JLabel predatorSpeedLabel=new JLabel("Predator Speed[2.1]");
	JLabel predatorForceLabel=new JLabel("Predator Max Force[0.3]");
	JLabel predatorLifeTimeLabel=new JLabel("Predator Life[1.2k]");
	JLabel predatorRepDeltaLabel=new JLabel("Predator Re Delta[260]");
	JLabel predatorAdulthoodLabel=new JLabel("Predator Adult[600]");
	JLabel predatorHealthLabel=new JLabel("Predator Health[400]");
	JLabel predatorVisionLabel=new JLabel("Predator Vision[800]");
	
	JTextField predatorSpeed=new JTextField();
	JTextField predatorForce=new JTextField();
	JTextField predatorLifeTime=new JTextField();
	JTextField predatorRepDelta=new JTextField();
	JTextField predatorAdulthood=new JTextField();
	JTextField predatorHealth=new JTextField();
	JTextField predatorVision=new JTextField();
	
	JLabel creatureSpeedLabel=new JLabel("Creature Speed[1.9]");
	JLabel creatureForceLabel=new JLabel("Creature Max Force[0.2]");
	JLabel creatureLifeTimeLabel=new JLabel("Creature Life[1k]");
	JLabel creatureRepDeltaLabel=new JLabel("Creature RepDelta[260]");
	JLabel creatureAdulthoodLabel=new JLabel("Creature Adult[800]");
	JLabel creatureHealthLabel=new JLabel("Creature Health[350]");
	JLabel creatureVisionLabel=new JLabel("Creature Vision[300]");
	
	JTextField creatureSpeed=new JTextField();
	JTextField creatureForce=new JTextField();
	JTextField creatureLifeTime=new JTextField();
	JTextField creatureRepDelta=new JTextField();
	JTextField creatureAdulthood=new JTextField();
	JTextField creatureHealth=new JTextField();
	JTextField creatureVision=new JTextField();
	
	
	JScrollPane scrollPane=new JScrollPane(centerPanel);
	JScrollPane mainscrollPane=new JScrollPane(mainPanel);
	JCheckBox debugCheckBox=new JCheckBox("Debug visualization");
	AnimosityListener listener=new AnimosityListener(this);
	
	
	AnimosityFrame(String n){
		name=n;
		mainscrollPane.getVerticalScrollBar().setUnitIncrement(20);
		this.setTitle(name+version);
		gridPanel.add(predatorSpeedLabel);
		gridPanel.add(predatorSpeed);
		gridPanel.add(creatureSpeedLabel);
		gridPanel.add(creatureSpeed);
		gridPanel.add(predatorForceLabel);
		gridPanel.add(predatorForce);
		gridPanel.add(creatureForceLabel);
		gridPanel.add(creatureForce);
		gridPanel.add(predatorLifeTimeLabel);
		gridPanel.add(predatorLifeTime);
		gridPanel.add(creatureLifeTimeLabel);
		gridPanel.add(creatureLifeTime);
		gridPanel.add(predatorRepDeltaLabel);
		gridPanel.add(predatorRepDelta);
		gridPanel.add(creatureRepDeltaLabel);
		gridPanel.add(creatureRepDelta);
		gridPanel.add(predatorAdulthoodLabel);
		gridPanel.add(predatorAdulthood);
		gridPanel.add(creatureAdulthoodLabel);
		gridPanel.add(creatureAdulthood);
		gridPanel.add(predatorHealthLabel);
		gridPanel.add(predatorHealth);
		gridPanel.add(creatureHealthLabel);
		gridPanel.add(creatureHealth);
		gridPanel.add(predatorVisionLabel);
		gridPanel.add(predatorVision);
		gridPanel.add(creatureVisionLabel);
		gridPanel.add(creatureVision);
		updateText();
		subNorth.add(gridPanel,BorderLayout.CENTER);
		subNorth.add(B_clearGraph,BorderLayout.NORTH);
		subNorth.add(B_updateValues,BorderLayout.SOUTH);
		northPanel.add(graphPanel,BorderLayout.EAST);
		northPanel.add(subNorth);
		centerPanel.add(simulation);
		southPanel.add(B_generate_creature);
		southPanel.add(B_generate_predator);
		southPanel.add(B_generate_plant);
		southPanel.add(B_stop);
		southPanel.add(B_resume);
		southPanel.add(B_savePNG);
		southPanel.add(debugCheckBox);
		northPanel.setBackground(Utilities.slateGrey);
		centerPanel.setBackground(Utilities.slateGrey);
		southPanel.setBackground(Utilities.slateGrey);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.B_updateValues.addActionListener(listener);
		this.B_updateValues.setActionCommand("UPDATEVALUES");
		this.B_clearGraph.addActionListener(listener);
		this.B_clearGraph.setActionCommand("CLEARGRAPHS");
		this.B_generate_creature.addActionListener(listener);
		this.B_generate_creature.setActionCommand("GENERATEPOINT");
		this.B_generate_plant.addActionListener(listener);
		this.B_generate_plant.setActionCommand("GENERATEPLANT");
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
		this.B_generate_plant.setEnabled(false);
		this.B_generate_predator.setEnabled(false);
		this.B_resume.setEnabled(true);
		this.add(mainscrollPane);
		this.setSize(new Dimension(1600,900));
		this.setVisible(true);
		
	}


	private void updateText() {
		this.predatorSpeed.setText("2.1");
		this.predatorForce.setText("0.3");
		this.predatorLifeTime.setText("1200");
		this.predatorRepDelta.setText("260");
		this.predatorAdulthood.setText("600");
		this.predatorHealth.setText("400");
		this.predatorVision.setText("800");
		
		this.creatureSpeed.setText("1.9");
		this.creatureForce.setText("0.2");
		this.creatureLifeTime.setText("1000");
		this.creatureRepDelta.setText("260");
		this.creatureAdulthood.setText("800");
		this.creatureHealth.setText("350");
		this.creatureVision.setText("300");
		
	}
}
