package maps;
import java.util.ArrayList;
import java.awt.Color;
import props.*;

public class ForestMapTest 
{
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<Tree> trees = new ArrayList<Tree>();
	public ArrayList<River> rivers = new ArrayList<River>();
	public ArrayList<Bridge> bridges = new ArrayList<Bridge>();
	public ArrayList<Floor> floors = new ArrayList<Floor>();
	
	//Custom Colors:
	Color CabinRoof = new Color(105, 65, 45);
	Color Grass = new Color (0, 100, 0);
	
	public void addProps()
	{
		//Floors:
		floors.add(new Floor(0, 0, 750, 750, Grass));
		
		//Buildings:
		buildings.add(new Building(0, 75, 100, 100, CabinRoof));
	}
}
