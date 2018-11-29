package props;
import java.awt.*;

public class Building 
{
	//Coordinates of the Building:
	int xCord;
	int yCord;
	
	//Width and Height of the Building:
	int bWidth;
	int bHeight;
	
	//Colors of the Building's Roof:
	Color Roof;
	
	public Building(int x, int y, int w, int h, Color c)
	{
		xCord = x;
		yCord = y;
		
		bWidth = w;
		bHeight = h;
		
		Roof = c;
		System.out.println("Building Created.");
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Roof);
		g.fillRect(xCord, yCord, bWidth, bHeight);
	}
}
