import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


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
				Utilities.saveChartToPNG(frm.graphPanel.areaChart, "areachart", 1920, 1080);
				Utilities.saveChartToPNG(frm.graphPanel.XYChart, "xychart", 1920, 1080);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(arg0.getActionCommand().equals("CLEARGRAPHS")) {
			frm.graphPanel.xySpeciesOne.clear();
			frm.graphPanel.xySpeciesTwo.clear();
			frm.graphPanel.xySpeciesThree.clear();
			frm.graphPanel.xyPopulationToT.clear();
		}
		else if(arg0.getActionCommand().equals("UPDATEVALUES")) {
			CreatureTriangle.maxspeedVal=Float.parseFloat(frm.predatorSpeed.getText());
			CreatureTriangle.maxforceVal=Float.parseFloat(frm.predatorForce.getText());
			CreatureTriangle.lifetimeVal=Integer.parseInt(frm.predatorLifeTime.getText());
			CreatureTriangle.repdeltaVal=Integer.parseInt(frm.predatorRepDelta.getText());
			CreatureTriangle.adulthoodVal=Integer.parseInt(frm.predatorAdulthood.getText());
			CreatureTriangle.healthVal=Integer.parseInt(frm.predatorHealth.getText());
			CreatureTriangle.perceptionRadVal=Integer.parseInt(frm.predatorVision.getText());
			CreaturePoint.maxspeedVal=Float.parseFloat(frm.creatureSpeed.getText());
			CreaturePoint.maxforceVal=Float.parseFloat(frm.creatureForce.getText());
			CreaturePoint.lifetimeVal=Integer.parseInt(frm.creatureLifeTime.getText());
			CreaturePoint.repdeltaVal=Integer.parseInt(frm.creatureRepDelta.getText());
			CreaturePoint.adulthoodVal=Integer.parseInt(frm.creatureAdulthood.getText());
			CreaturePoint.healthVal=Integer.parseInt(frm.creatureHealth.getText());
			CreaturePoint.perceptionRadVal=Integer.parseInt(frm.creatureVision.getText());
			
		}else if (arg0.getSource().equals(frm.debugCheckBox)){
			if(frm.debugCheckBox.isSelected()) {
				frm.simulation.debugging=true;
			}else {
				frm.simulation.debugging=false;
			}
		}
	}
}
