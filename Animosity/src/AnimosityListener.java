import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBox;

public class AnimosityListener implements ActionListener{
	AnimosityFrame frm;
	
	AnimosityListener(AnimosityFrame frm){
		this.frm=frm;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("GENERATEPOINT")){
			frm.simulation.generateCreaturePoint(Utilities.RNGLocX(),Utilities.RNGLocY());
		}
		else if (arg0.getActionCommand().equals("GENERATEPREDATOR")){
			frm.simulation.generateCreatureTriangle(Utilities.RNGLocX(),Utilities.RNGLocY());
		}else if (arg0.getActionCommand().equals("GENERATEPLANT")) {
			frm.simulation.generatePlant_1(Utilities.RNGLocX(), Utilities.RNGLocY());
		}
		else if (arg0.getActionCommand().equals("RESUME")){
			frm.simulation.start();
			frm.B_generate_plant.setEnabled(true);
			frm.B_generate_creature.setEnabled(true);
			frm.B_generate_predator.setEnabled(true);
			frm.B_stop.setEnabled(true);
			frm.B_resume.setEnabled(false);
		}
		else if(arg0.getActionCommand().equals("STOP")){
			frm.simulation.stop();
			frm.B_generate_plant.setEnabled(false);
			frm.B_generate_creature.setEnabled(false);
			frm.B_generate_predator.setEnabled(false);
			frm.B_resume.setEnabled(true);
			frm.B_stop.setEnabled(false);
		}else if(arg0.getActionCommand().equals("SAVEGRAPHS")) {
			try {
				Utilities.saveChartToPNG(frm.northPanel.areaChart, "areachart", 1920, 1080);
				Utilities.saveChartToPNG(frm.northPanel.XYChart, "xychart", 1920, 1080);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (arg0.getSource().equals(frm.debugCheckBox)){
			if(frm.debugCheckBox.isSelected()) {
				frm.simulation.debugging=true;
			}else {
				frm.simulation.debugging=false;
			}
		}
	}
}
