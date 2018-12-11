package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Wall extends Rectangle
{
	//Color of the Wall:
	Color color;
	
	public Wall(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		this.color = c;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Wall:
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}
