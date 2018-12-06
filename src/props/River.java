package props;
import java.awt.*;

@SuppressWarnings("serial")
public class River extends Rectangle
{
	//Color of the River:
	Color riverColor = new Color(0, 20, 210);
	
	public River(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
	}
	
	public void paint(Graphics g)
	{
		//Draws the River:
		g.setColor(riverColor);
		g.fillRect(x, y, width, height);
	}
}
