package props;
import java.awt.*;

public class Tree 
{
	//Center Coordinates:
	int xCenter;
	int yCenter;
	int radius;
	
	int xCord;
	int yCord;
	
	Color treeColor = new Color(5, 85, 0);
	
	public Tree(int x, int y, int r)
	{
		xCenter = x;
		yCenter = y;
		radius = r;
		
		System.out.println("Tree Created.");
	}
	
	public void paint(Graphics g)
	{
		g.setColor(treeColor);
		g.fillOval(xCenter, yCenter, 
				   radius * 2, radius * 2);
	}
	
}
