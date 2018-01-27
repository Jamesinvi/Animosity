
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main {

	public static void main(String[] args){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		}
		try {
			System.setProperty("sun.java2d.opengl","True");
		}catch (Exception e) {
			
		}
		AnimosityFrame mainFrame=new AnimosityFrame("Anymosity");
	}
}
