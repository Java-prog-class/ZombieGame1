package props;
import java.awt.*;

public class Tree 
{
	//Center Coordinates:
	int xCenter;
	int yCenter;
	int radius;
	
	public Tree(int x, int y, int r)
	{
		xCenter = x;
		yCenter = y;
		radius = r;
		
		System.out.println("Tree Created.");
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillOval(xCenter, yCenter, radius * 2, radius * 2);
		g.setColor(Color.BLACK);
		g.drawOval(xCenter, yCenter, radius * 2, radius * 2);
	}
	
}
