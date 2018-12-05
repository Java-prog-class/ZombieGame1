package props;
import java.awt.*;

public class Bridge 
{
	//Coordinates of the Bridge:
	int xCord;
	int yCord;
	
	//Width and Height of the Bridge:
	int bWidth;
	int bHeight;
	
	//Custom Color of the Bridge:
	Color bridgeColor = new Color(145, 95, 25);
	
	public Bridge(int x, int y, int w, int h)
	{
		xCord = x;
		yCord = y;
		
		bWidth = w;
		bHeight = h;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(bridgeColor);
		g.fillRect(xCord, yCord, bWidth, bHeight);
		g.setColor(Color.BLACK);
		g.drawRect(xCord, yCord, bWidth, bHeight);
	}
}
