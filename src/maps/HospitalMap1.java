package maps;
import java.util.ArrayList;
import java.awt.Color;
import props.*;

public class HospitalMap1 
{
	public static ArrayList<Floor> floors = new ArrayList<Floor>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	
	//Custom Colors:
	Color tile1Color = new Color(195, 195, 195); //Grey.
	Color tile2Color = new Color(0, 64, 128);    //Blue.
	
	Color wallColor = new Color (127, 127, 127);
	
	public void addProps()
	{
		//Floors:
		floors.add(new Floor(0, 0, 500, 500, tile1Color));
		
		//Walls:
		walls.add(new Wall(100, 200, 50, 50, wallColor));
	}
}
