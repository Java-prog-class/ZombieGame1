package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Bridge extends Rectangle
{
	//Custom Color of the Bridge:
	Color bridgeColor = new Color(145, 95, 25);
	
	public Bridge(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Bridge:
		g.setColor(bridgeColor);
		g.fillRect(x, y, width, height);
		
		//Draws the Outline:
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}
