package maps;
import java.util.*;
import java.awt.Color;
import props.*;

public class TestMap
{
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<Tree> trees = new ArrayList<Tree>();
	
	//Custom Colors:
	Color RedRoof = new Color(112, 4, 4);
	
	
	public void addProps()
	{
		buildings.add(new Building(20, 20, 100, 100, RedRoof));
		buildings.add(new Building(400, 300, 40, 60, Color.GREEN));
		
		trees.add(new Tree(200, 100, 20));
	}
}