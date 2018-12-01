package me.zombies;

//Java Imports:
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

//Package Imports:
import maps.*;
import props.*;

public class Main implements KeyListener, MouseListener, MouseMotionListener
{
	//JFrame and DrawingPanel Creations:
	final static int WIN = 500;
	static JFrame window;
	DrawingPanel drPanel = new DrawingPanel();
	
	//Colors, Font, and Stroke:
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	Font HPBar = new Font("Arial", Font.PLAIN, WIN/30);
	BasicStroke stroke = new BasicStroke(WIN/300);
	
	//Maps:
	ForestMapTest forestMapTest = new ForestMapTest();
	
	//Global Variables:
	static PlayerStats Player = new PlayerStats("Josh");	// <---- Creating the Player Object
	
	static int mouseX;	// <---- Mouse Variables
	static int mouseY;
	
	static boolean W = false,	// <---- Input variables for the player.
			       A = false,	//		 These variables are set to false,
			       S = false,	//       when the key is pressed or mouse
			       D = false,	//		 button is clicked, the corresponding
			      M1 = false,	//		 variable is set to true
			      M2 = false;
	
	Timer timer;	// <---- Initializes the Timer
	int tSpeed = 1;	// <---- The Timer's Speed
	
	public static void main (String [] args) 
	{
		new Main();
	}
	
	Main() 
	{
		//JFrame Setup:
		window = new JFrame("Zombie Game");						// <---- Sets the titles
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// <---- Allows for closing
		window.setResizable(false);								// <---- Turns of resizing
		drPanel.addKeyListener(this);							// <---- Adds the keyboard listener to the drPanel
		drPanel.addMouseListener(this);							// <---- Adds the mouse listener to the drPanel
		drPanel.addMouseMotionListener(this);					// <---- Adds the mouse motion listener to the drPanel
		forestMapTest.addProps();                               // <---- Adds the props found on the map
		window.add(drPanel);									// <---- Adds the drPanel to the Window
		window.pack();											// <---- Packs the Window
		window.setVisible(true);								// <---- Sets it visible
	
		timer = new Timer(tSpeed, new TimerListener());			// <---- Creates the Timer
		timer.start();											// <---- Starts the Timer
	}
	
	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel 
	{
		DrawingPanel() 
		{
			this.setPreferredSize(new Dimension (WIN, WIN));	// <---- Sets the Size
			this.setBackground(White);							// <---- Sets the background color
		}
		
		@Override
		public void paintComponent(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;	
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			super.paintComponent(g);
			
			//Draws all the Props:
			//These must be called first otherwise they draw over the UI and Player:
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
			
			g2.setStroke(stroke);
			this.requestFocus();
			
			//Draws the Player:
			BufferedImage img = null;
			try { img = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
			} catch (IOException e) {}
	
			g2.rotate(Math.toRadians(Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));		// <---- Rotates the whole screen	
			g.drawImage(img, Player.x, Player.y,Player.width, Player.height, drPanel);							// <---- Draws the Player
			g2.rotate(Math.toRadians(-Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));	// <---- Rotates the whole screen back
			
			
		//Draw the Health Bar
			int BarWidth = WIN/3; 	// <---- Constant Ratios based off of the Screen Width
			int BarHeight = WIN/30;
			
			g.setColor(White);										// <---- White Background
			g.fillRect(BarHeight, BarHeight, BarWidth, BarHeight);
			
			g.setColor(Red);										// <---- Red Health Meter
			int HPBarWidth = (int) (Player.PercentHP*BarWidth); 	// <---- The Size of the Meter based of the Health Precentage
			g.fillRect(BarHeight, BarHeight, HPBarWidth, BarHeight);
			
			g.setColor(Black);										// <---- Black Boarder
			g.drawRect(BarHeight, BarHeight, BarWidth, BarHeight);
			
			g2.setFont(HPBar);										// <---- Display Text of HP
			String HPString = Player.HP+"/"+Player.maxHP;
			g.drawString(HPString, (int)(WIN/3.6), WIN/16);
		}		

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_W) W = true;
		if (e.getKeyCode()==KeyEvent.VK_A) A = true;
		if (e.getKeyCode()==KeyEvent.VK_S) S = true;
		if (e.getKeyCode()==KeyEvent.VK_D) D = true;	
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_W) W = false;
		if (e.getKeyCode()==KeyEvent.VK_A) A = false;
		if (e.getKeyCode()==KeyEvent.VK_S) S = false;
		if (e.getKeyCode()==KeyEvent.VK_D) D = false;	
		
	}
	
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
	// ----- Stuff that happens every frame of the game -----
			
		//Movement	
			if (W && Player.y>=0) Player.y-=Player.speed;					// <---- Moving Up
			if (A && Player.x>=0) Player.x-=Player.speed;					// <---- Moving Left
			if (S && Player.y<=WIN-Player.height) Player.y+=Player.speed;	// <---- Moving Down
			if (D && Player.x<=WIN-Player.height) Player.x+=Player.speed;	// <---- Moving Right
			
		//Rotation
			int deltaX = mouseX-Player.x; 									// <---- Subtracting the Player location from the Mouse Location
			int deltaY = mouseY-Player.y;
			Player.angle = Math.toDegrees(Math.atan2(deltaX, -deltaY)); 	// <---- The angle of rotation
			
		//Death check
			if (Player.HP<=0) Player.alive = false;
			
			window.repaint();
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getButton()==MouseEvent.BUTTON1) M1 = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (e.getButton()==MouseEvent.BUTTON1) M1 = false;
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

//UNUSED METHOD
	public void keyTyped(KeyEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	
}