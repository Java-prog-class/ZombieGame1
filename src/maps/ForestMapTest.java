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
		floors.add(new Floor(0, 0, 500, 500, Grass));
		
		//Buildings:
		buildings.add(new Building(400, 0, 100, 95, CabinRoof));
		buildings.add(new Building(0, 100, 100, 100, CabinRoof));
		buildings.add(new Building(0, 300, 100, 100, CabinRoof));
		
		//Rivers:
		//Do NOT let the Rivers and Bridge Intersect:
		rivers.add(new River(300, 0, 30, 50));
		rivers.add(new River(300, 90, 30, 110));
		rivers.add(new River(300, 200, 200, 30));
		
		//Bridges:
		bridges.add(new Bridge(285, 50, 60, 40));
	}
}
