package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Water extends Rectangle
{
	//Color of the Water:
	Color waterColor = new Color(0, 20, 210);
	
	public Water(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
	}
	
	public void paint(Graphics g)
	{
		//Draws the River:
		g.setColor(waterColor);
		g.fillRect(x, y, width, height);
	}
}
