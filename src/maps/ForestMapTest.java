package maps;
import java.util.ArrayList;
import java.awt.Color;
import props.*;

public class ForestMapTest 
{
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<Tree> trees = new ArrayList<Tree>();
	public ArrayList<Water> waters = new ArrayList<Water>();
	public ArrayList<Bridge> bridges = new ArrayList<Bridge>();
	public ArrayList<Floor> floors = new ArrayList<Floor>();
	
	//Custom Colors:
	Color CabinRoof = new Color(105, 65, 45);
	Color Grass = new Color (0, 100, 0);
	
	//Adds all Props to the Map:
	public void addProps()
	{
		//Floors:
		floors.add(new Floor(0, 0, 750, 750, Grass));
		
		//Buildings:
		buildings.add(new Building(130,   0, 80, 80, CabinRoof));
		buildings.add(new Building(0,   200, 80, 80, CabinRoof));
		buildings.add(new Building(0,   450, 80, 80, CabinRoof));
		buildings.add(new Building(300, 200, 80, 80, CabinRoof));
		buildings.add(new Building(300, 450, 80, 80, CabinRoof));

		buildings.add(new Building(580, 0, 100, 100, CabinRoof));
		
		
		//Water:
		waters.add(new Water(400, 600, 350, 350));
		waters.add(new Water(430, 0, 50, 200));
		waters.add(new Water(430, 200, 600, 50));
	}
}
