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
			generateCreaturePoint();
		}
		else if (arg0.getActionCommand().equals("GENERATEPREDATOR")){
			generateCreatureTriangle();
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
	//CREATURE GENERATOR METHODS
	void generateCreaturePoint(){
		frm.creatures.add(new CreaturePoint(frm.simulation,100,100,20));
	}
	void generateCreatureTriangle(){
		frm.creatures.add(new CreatureTriangle(frm.simulation,100,100,59));
	}
}
