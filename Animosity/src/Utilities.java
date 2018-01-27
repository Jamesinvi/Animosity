import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class Utilities {
	static final Color back_Green=new Color(54,145,70);
	static final Color my_Brown=new Color(139, 69, 19);
	static final Color my_blue=new Color(0,0,1.0f,0.3f);
	static final Color my_black=new Color (0,0,0,0.3f);
	static final Color my_green=new Color(110,228,0);
	static final Color slateGrey=new Color(112,128,144);
	static Random random=new Random();
	static 	public int RNGLocX() {
		int res=random.nextInt(Simulation.WIDTH);
		return res;
	}
	static public int RNGLocY() {
		int res=random.nextInt(Simulation.HEIGHT);
		return res;
	}
	public static float randFloat(float min, float max) {
	    float result = random.nextFloat() * (max - min) + min;
	    return result;

	}
	final static String saveChartToPNG(final JFreeChart chart, String fileName, final int width, final int height) throws IOException {
        String result = null;
        
        if (chart != null) {
            if (fileName == null) {
                final String chartTitle = chart.getTitle().getText();
                if (chartTitle != null) {
                    fileName = chartTitle;
                } else {
                    fileName = "chart";
                }
            }
            result = fileName+".png";
            ChartUtilities.saveChartAsPNG(new File(result), chart, width, height);
        }
        
        return result;
    }
	static float map(float value, float start1, float stop1, float start2, float stop2){
		float t1=stop1-start1;
		float t2=stop2-start2;
		float res=value*(t2/t1);
		//System.out.println("Value: "+value+" Start1: "+start1+" Stop1: "+stop1+" Start2: "+start1+" Stop2: "+stop2);
		//System.out.println("t1: "+t1+" t2: "+t2+"res: "+res);
		return res;
	}
}
