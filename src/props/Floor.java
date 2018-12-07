package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Floor extends Rectangle 
{
	//Color of the Floor:
	Color Floor;
	
	public Floor(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		Floor = c;
	}
	
	public void paint(Graphics g)
	{
		//Draws the Floor:
		g.setColor(Floor);
		g.fillRect(x, y, width, height);
	}
}
