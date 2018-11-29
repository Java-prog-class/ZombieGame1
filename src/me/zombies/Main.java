package me.zombies;

//Java Imports:
import javax.swing.*;
import java.awt.*;

//Player Imports:

//Map Imports:
import maps.*;
import props.*;

public class Main 
{
	//Global Variables:
	JFrame window;
	DrawingPanel drPanel;
	
	//Window Width and Height:
	final static int winX = 600;
	final static int winY = 600;
	
	//Custom Colors:
	Color grassColor = new Color(15, 80, 0);
	
	//Maps:
	ForestMapTest forestMapTest = new ForestMapTest();
	
	public static void main (String [] args)
	{
		new Main();
	}
	
	Main()
	{
		//JFrame Setup:
		window = new JFrame("Zombie Game");
		drPanel = new DrawingPanel();
		forestMapTest.addProps();
		
		window.setSize(winX, winY);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.add(drPanel);
		window.setVisible(true);
	}
	
	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel
	{
		DrawingPanel()
		{
			this.setBackground(grassColor);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			for (Building b : forestMapTest.buildings)
			{
				b.paint(g);
			}
			
			for (Tree t : forestMapTest.trees)
			{
				t.paint(g);
			}
			
			for (River r : forestMapTest.rivers)
			{
				r.paint(g);
			}
			
			for (Bridge b : forestMapTest.bridges)
			{
				b.paint(g);
			}
		}
	}
}
