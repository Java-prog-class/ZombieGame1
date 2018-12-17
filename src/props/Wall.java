package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Wall extends Rectangle
{
	//Color of the Wall:
	Color wallColor;
	
	public Wall(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		wallColor = c;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Wall:
		g.setColor(wallColor);
		g.fillRect(x, y, width, height);
	}
}
