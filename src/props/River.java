package props;
import java.awt.*;

public class River 
{
	//Coordinates of the River:
	int xCord;
	int yCord;
	
	//Width and Height of the River:
	int rWidth;
	int rHeight;
	
	//Colors of the River:
	Color River;
	
	public River(int x, int y, int w, int h, Color c)
	{
		xCord = x;
		yCord = y;
		
		rWidth = w;
		rHeight = h;
		
		River = c;
		System.out.println("River Created.");
	}
	
	public void paint(Graphics g)
	{
		g.setColor(River);
		g.fillRect(xCord, yCord, rWidth, rHeight);
	}
}
