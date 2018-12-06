package props;
import java.awt.*;

@SuppressWarnings("serial")
public class Building extends Rectangle
{
	//Colors of the Building's Roof:
	Color Roof;
	
	public Building(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		Roof = c;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Roof);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}
