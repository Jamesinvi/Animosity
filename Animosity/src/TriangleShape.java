import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class TriangleShape {
	protected int height;
	protected int width;
	protected Color color;
	public TriangleShape(int width, int height, Color color)
	{
		this.height = height;
		this.width= width;
		this.color= color;
		
		
	}
	//COSTUM DRAW METHOD FOR TRIANGLE
	public void drawMe(Graphics2D g, Vector location)
	{
		g.setColor(color);
		int x=(int)location.getX();
		int y=(int)location.getY();

		Point point2 = new Point(x+width,y);
		Point point3 = new Point(x+(width/2),y - height);
		g.drawLine(x,y,point2.x,point2.y);
		g.drawLine(x,y,point3.x,point3.y);
		g.drawLine(point2.x,point2.y,point3.x,point3.y);
					
	}
}
