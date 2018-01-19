import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			frm.generateCreaturePoint(Utilities.RNGLocX(),Utilities.RNGLocY());
		}
		else if (arg0.getActionCommand().equals("GENERATEPREDATOR")){
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
	}
}
