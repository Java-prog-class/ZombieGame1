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
	
	//Custom Colors:
	Color CabinRoof = new Color(105, 65, 45);
	
	public void addProps()
	{
		buildings.add(new Building(300, 100, 100, 100, CabinRoof));
	}
}
