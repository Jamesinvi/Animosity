
public class Utilities {
	static 	public int RNGLocX() {
		int res=(int)(Math.random()*Simulation.WIDTH);
		return res;
	}
	static public int RNGLocY() {
		int res=(int)(Math.random()*Simulation.HEIGHT);
		return res;
	}
	static float map(float value, float start1, float stop1, float start2, float stop2){
		//value:stop1-start1=x:stop2-start1
		float t1=stop1-start1;
		float t2=stop2-start2;
		float res=value*(t2/t1);
		//System.out.println("Value: "+value+" Start1: "+start1+" Stop1: "+stop1+" Start2: "+start1+" Stop2: "+stop2);
		//System.out.println("t1: "+t1+" t2: "+t2+"res: "+res);
		return res;
	}
}
