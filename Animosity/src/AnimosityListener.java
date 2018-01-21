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
		if (arg0.getActionCommand().equals("GENERATE")){
			frm.B_resume.setEnabled(true);
			frm.B_stop.setEnabled(true);
			if(frm.creatures.size()==500){
				frm.makeSpace();
			}
			frm.generateCreaturePoint(Utilities.RNGLocX(),Utilities.RNGLocY());
		}
		else if (arg0.getActionCommand().equals("GENERATEPREDATOR")){
			if(frm.creatures.size()==500){
				frm.makeSpace();
			}
			frm.generateCreatureTriangle(Utilities.RNGLocX(),Utilities.RNGLocY());
		}
		else if (arg0.getActionCommand().equals("RESUME")){
			frm.simulation.start();
			frm.B_generate_creature.setEnabled(true);
			frm.B_generate_predator.setEnabled(true);
			frm.B_stop.setEnabled(true);
			frm.B_resume.setEnabled(false);
		}
		else if(arg0.getActionCommand().equals("STOP")){
			frm.simulation.stop();
			frm.B_generate_creature.setEnabled(false);
			frm.B_generate_predator.setEnabled(false);
			frm.B_resume.setEnabled(true);
			frm.B_stop.setEnabled(false);
		}
		else if(arg0.getActionCommand().equals("SAVEGRAPHS")) {
			try {
				Utilities.saveChartToPNG(frm.northPanel.areaChart, "areachart", 1920, 1080);
				Utilities.saveChartToPNG(frm.northPanel.XYChart, "xychart", 1920, 1080);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
