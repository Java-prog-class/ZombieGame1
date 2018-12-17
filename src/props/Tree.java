package props;
import java.awt.*;

public class Tree 
{
	//Center Coordinates:
	int xCenter;
	int yCenter;
	int radius;
	
	Color treeColor = new Color(0, 128, 0);
	
	public Tree(int x, int y, int r)
	{
		xCenter = x;
		yCenter = y;
		radius = r;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(treeColor);
		g.fillOval(xCenter, yCenter, radius * 2, radius * 2);
		g.setColor(Color.BLACK);
		g.drawOval(xCenter, yCenter, radius * 2, radius * 2);
	}
	
}
