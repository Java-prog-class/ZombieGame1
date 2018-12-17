package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Building extends Rectangle
{
	//Colors of the Building's Roof:
	Color roofColor;
	
	public Building(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		roofColor = c;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Building:
		g.setColor(roofColor);
		g.fillRect(x, y, width, height);
		
		//Draws the Outline:
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}
