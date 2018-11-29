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
	
	public River(int x, int y, int w, int h)
	{
		xCord = x;
		yCord = y;
		
		rWidth = w;
		rHeight = h;
		
		System.out.println("River Created.");
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(xCord, yCord, rWidth, rHeight);
	}
}
