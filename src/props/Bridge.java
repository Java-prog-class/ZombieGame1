package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Bridge extends Rectangle
{
	//Custom Color of the Bridge:
	Color color;
	
	//Type of Bridge 1 is Horizontal, 2 is Vertical:
	public int type;
	
	public Bridge(int x, int y, int w, int h, Color c, int t)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		this.color = c;
		this.type = t;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Bridge:
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		//Draws the Outline:
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}
