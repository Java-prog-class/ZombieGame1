package maps;
import java.util.ArrayList;
import java.awt.Color;
import props.*;

public class TestMap
{
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<Tree> trees = new ArrayList<Tree>();
	public ArrayList<River> rivers = new ArrayList<River>();
	
	//Custom Colors:
	Color RedRoof = new Color(112, 4, 4);
	Color BlueRoof = new Color(4, 4, 112);
	
	
	public void addProps()
	{
		buildings.add(new Building(20, 20, 100, 100, RedRoof));
		buildings.add(new Building(400, 300, 40, 60, BlueRoof));
		
		trees.add(new Tree(200, 100, 20));
		
		rivers.add(new River(0, 400, 300, 50));
	}
}