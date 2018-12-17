package me.zombies;

//Java Imports:
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;

//Package Imports:
import maps.*;
import props.*;

public class Main implements KeyListener, MouseListener, MouseMotionListener
{
	//JFrame and DrawingPanel Creations:
	final static int WIN = 750;
	static JFrame window;
	DrawingPanel drPanel = new DrawingPanel();
	
	//Colors, Font, and Stroke:
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	Color Grass = new Color(0, 64, 0);
	Font HPBar = new Font("Arial", Font.PLAIN, WIN/30);
	Font HPBarFont = new Font("Arial", Font.PLAIN, WIN/30);
	Font ZombiesCounterFont = new Font("Arial", Font.BOLD, WIN/15);
	BasicStroke stroke = new BasicStroke(WIN/300);
	
	//Maps:
	ForestMapTest fmt = new ForestMapTest();
	
	//Global Variables:
	static PlayerStats Player = new PlayerStats("Josh");	// <---- Creating the Player Object
	
	//Variables for shooting
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();	// <---- Array list for bullets

	//Constants for Zombie Types
	int GreenZombie = 1;
	int BlueZombie =  2;
	int RedZombie =   3;
	int GoldZombie =  4;
	
	//Zombies
	ArrayList<Zombies> zombies = new ArrayList<Zombies>();
	int Round = 0;
	int ZombiesCounter = 0;
	Zombies z;
	
	//Rotation of Player
	int deltaX = mouseX-Player.x; 	// <---- Subtracting the Player location from the Mouse Location
	int deltaY = mouseY-Player.y;

	//Weapons
	Weapon pistol = new Weapon(5, 7, "Pistol");
	int fire;
	int magX, magY;
	
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
	long lastHit = 0L;
	
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
		fmt.addProps();                               			// <---- Adds the props found on the map
		window.add(drPanel);									// <---- Adds the drPanel to the Window
		fmt.addProps();											// <---- Adds all the Props from ForestMapTest
		window.pack();											// <---- Packs the Window
		window.setVisible(true);								// <---- Sets it visible
		
		Magazine();
		
		timer = new Timer(tSpeed, new TimerListener());			// <---- Creates the Timer
		timer.start();											// <---- Starts the Timer
	}

	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel 
	{
		DrawingPanel() 
		{
			this.setPreferredSize(new Dimension (WIN, WIN));	// <---- Sets the Size
		}
		
		@Override
		public void paintComponent(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;	
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			super.paintComponent(g);
			this.requestFocus();
			
			//Draws all the Props:
			//These must be called first otherwise they draw over the UI and Player:
			for (Floor f : fmt.floors) f.paint(g);
			for (Building b : fmt.buildings) b.paint(g);
			for (Water w : fmt.waters) w.paint(g);
			
			drawMagazine(g);
			drawPlayer(g, g2);
			addBullets(g);
			drawZombies(g, g2);
			drawPlayerHealthBar(g, g2);
			drawZombieCounter(g, g2);
		}
	}
	
	//Draws the Player:
	void drawPlayer(Graphics g, Graphics2D g2)
	{
		BufferedImage img = null;
		try { img = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
		} catch (IOException e) {}

		g2.rotate(Math.toRadians(Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));		// <---- Rotates the whole screen	
		g.drawImage(img, Player.x, Player.y, Player.width, Player.height, drPanel);							// <---- Draws the Player
		g2.rotate(Math.toRadians(-Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));	// <---- Rotates the whole screen back
	}
	
	void movePlayer()
	{
		double vx;
		double vy;
		double moveAngle;
		
		//Moving Forward:
		if (W) 
		{								
			moveAngle = Math.toRadians(Player.angle-90);

			vx = Player.speed*Math.cos(moveAngle);	// <----Gets the horizontal speed based off the angle
			vy = Player.speed*Math.sin(moveAngle);	// <----Gets the  vertical  speed based off the angle

			Player.y+=vy; 	// <---- Moves the player in accordance with
			Player.x+=vx;	//		 it's vertical and horizontal speeds
			
		}

		//Moving Back:
		if (S) 
		{								
			moveAngle = Math.toRadians(Player.angle+90);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;
		}

		//Circling Right (Counter Clockwise)
		if (D) 
		{								
			moveAngle = Math.toRadians(Player.angle);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;

		}

		//Circling Left (Clockwise)
		if (A) 
		{								
			moveAngle = Math.toRadians(Player.angle-180);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;
		}	
		
		//Collision:
		Rectangle player = new Rectangle(Player.x, Player.y, Player.width, Player.height);
		
		//Building Collision:
		for (Building b : fmt.buildings) 
		{
			if (player.intersects(b))
			{
				//Horizontal Collision of the Building:
				if (b.x < Player.x && Player.x > b.width)
				{
					if (b.y < Player.y && Player.y > b.height)
					{
						//Bottom Side of the Building:
						if (W || S || A || D)
						{
							Player.y += 3;
						}
						
					} else {
						
						//Top Side of the Building:
						if (W || S || A || D)
						{
							Player.y -= 3;
						}
					}
				}
				
				//Vertical Collision of the Building:
				
			}
		}
	}
		
	void drawPlayerHealthBar(Graphics g, Graphics2D g2)
	{
		//Draw the Health Bar:
		int BarWidth = WIN/3; 	// <---- Constant Ratios based off of the Screen Width
		int BarHeight = WIN/30;
		
		g.setColor(White);										// <---- White Background
		g.fillRect(BarHeight, BarHeight, BarWidth, BarHeight);
		
		g.setColor(Red);										// <---- Red Health Meter
		int HPBarWidth = (int) (Player.PercentHP*BarWidth); 	// <---- The Size of the Meter based of the Health Percentage
		g.fillRect(BarHeight, BarHeight, HPBarWidth, BarHeight);
		
		g.setColor(Black);										// <---- Black Boarder
		g.drawRect(BarHeight, BarHeight, BarWidth, BarHeight);
		
		g2.setFont(HPBar);										// <---- Display Text of HP
		String HPString = Player.HP+"/"+Player.maxHP;
		g.drawString(HPString, (int)(WIN/3.6), WIN/16);
	}		
	
	//Draw the Zombies:
	void drawZombies(Graphics g, Graphics2D g2)
	{
		for (Zombies z : zombies)
		{
			g.drawImage(z.Img, z.x, z.y, z.ZombiesWidth, z.ZombiesWidth, drPanel);
			z.drawZombiesHealthBar(g, g2);
		}
	}
	
	//Guns:
	private void guns()
	{
		int ammo = fire;
		
		if (fire > 0)
		{
			if (ammo < pistol.ammo)
			{
				 bullets.add(new Bullet(Player));
				 ammo--;
			}
		}
	}
	
	//Draws Bullets:
	void addBullets(Graphics g)
	{
		for (Bullet b : bullets)
		{
			b.paint(g);
		}
	}
	
	//Ammo:
	void Magazine()
	{
		//int magAmmo = pistol.ammo;
		
		magX = (int)(Math.random() * WIN);
		magY = (int)(Math.random() * WIN);
		
		if (Player.x == magX)
		{
			fire = fire - pistol.ammo;
		}
	}
	
	private void drawMagazine(Graphics g)
	{
		g.setColor(Color.ORANGE);
		g.fillRect(magX, magY, 5, 10);
	}
	
	//Draw the Zombie Counter:
	void drawZombieCounter(Graphics g, Graphics2D g2)
	{
		FontMetrics fontMetrics = g2.getFontMetrics(ZombiesCounterFont);
		
		String str = null;
		if (ZombiesCounter < 10) str = "0"+ZombiesCounter;
		else str = ""+ZombiesCounter;
		
		g.setColor(Black);
		g.setFont(ZombiesCounterFont);
		
		int rightAlign = (WIN / 30) * 29;
		
		g.drawString(str, (rightAlign - fontMetrics.stringWidth(str)), WIN/16);
	}
	
	//Add Zombies:
	void addZombies()
	{
		Round++;
		ZombiesCounter = 0;
		
		//Round 1 Zombie Adding
		if (Round==1) {
			for (int i=0; i<15; i++) zombies.add(new Zombies(GreenZombie));
			ZombiesCounter = 15;
		}

		//Round 2 Zombie Adding
		if (Round==2) {
			for (int i=0; i<10; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i< 2; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 12;
		}

		//Round 3 Zombie Adding
		if (Round==3) {
			for (int i=0; i<10; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i< 6; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 16;
		}
		//Round 4 Zombie Adding
		if (Round==4) {
			for (int i=0; i<12; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i< 9; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 21;
		}

		//Round 5 Zombie Adding
		if (Round==5) {
			for (int i=0; i<13; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 1; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 24;
		}

		//Round 6 Zombie Adding
		if (Round==6) {
			for (int i=0; i<15; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i< 5; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 3; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 23;
		}			

		//Round 7 Zombie Adding
		if (Round==7) {
			for (int i=0; i<10; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 6; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 26;
		}

		//Round 8 Zombie Adding
		if (Round==8) {
			for (int i=0; i<10; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<05; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i<01; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 26;
		}

		//Round 9 Zombie Adding
		if (Round==9) {
			for (int i=0; i<10; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 8; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i< 5; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 33;
		}

		//Round 10 Zombie Adding
		if (Round==10) {
			for (int i=0; i<50; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<35; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<25; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 40;
		}
	}
	
	//Move Bullets:
	private void moveBullets()
	{
		//Move
		for (Bullet b : bullets)
		{
			b.move();
		}
		
		//Remove Circles when they're off the Screen:
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet b = bullets.get(i);
			if (b.x > WIN)
			{
				bullets.remove(i);
				i--;
			}
		}
	}
	
	//Moving the Zombies:
	@SuppressWarnings("unused")
	private void moveZombies()
	{
		for (Zombies z : zombies)
		{
			z.x += z.vx;
			z.y += z.vy;
			
			//Bouncing off the Walls:
			if (z.x-z.r < 0) z.vx *= -1;
			if (z.y-z.r < 0) z.vy *= -1;
			if (z.x+z.r > WIN) z.vx *= -1;
			if (z.y+z.r > WIN) z.vy *= -1;
		}
	}
	
	
	private class TimerListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// ------------------------------------------------------		
			// ----- Stuff that happens every frame of the game -----
			// ------------------------------------------------------
			
			Player.PercentRatio = ((Player.HP * 100) / Player.maxHP);
			Player.PercentHP = Player.PercentRatio / 100;
			
			for (Zombies z : zombies)
			{
				z.PercentRatio = ((z.HP * 100) / z.maxHP);
				z.PercentHP = z.PercentRatio / 100;
			}
			
			movePlayer();
			
			//moveZombies();
			
			moveBullets();
			
			
			//Rotation:
			int deltaX = mouseX-Player.x; 									// <---- Subtracting the Player location from the Mouse Location
			int deltaY = mouseY-Player.y;
			Player.angle = Math.toDegrees(Math.atan2(deltaX, -deltaY)); 	// <---- The angle of rotation
			
			//Player Death Check
			if (Player.HP<=0) Player.alive = false;

			
		//Zombie Hit Player Check
			for (Zombies z: zombies) {
				if (z.intersects(Player)) {
					long now = System.currentTimeMillis();
					if ((now-lastHit)>1000) {
						lastHit = now;
						Player.HP-=z.damage;
					}
				}
			}
			
		//Bullet Hit Zombie Check
			for (Zombies z: zombies) {
				for (int j=0; j<bullets.size(); j++) {
					
					Bullet b = bullets.get(j);
					if (z.intersects(b)) {
						z.HP-=5;
						bullets.remove(b);
					}
					
				}
				
			}

		//Zombie Death Check
			for (int i=0; i<zombies.size(); i++) {
				Zombies z = zombies.get(i);
				if (z.HP<=0) {
					zombies.remove(z);
					ZombiesCounter--;
				}
			}

		//Zombies Round Check
			if (ZombiesCounter<=0) addZombies();

		//Repaints the window (every frame)
			window.repaint();
		}
	}
	
	//Player Inputs:
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode()==KeyEvent.VK_W) W = true;
		if (e.getKeyCode()==KeyEvent.VK_A) A = true;
		if (e.getKeyCode()==KeyEvent.VK_S) S = true;
		if (e.getKeyCode()==KeyEvent.VK_D) D = true;	
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode()==KeyEvent.VK_W) W = false;
		if (e.getKeyCode()==KeyEvent.VK_A) A = false;
		if (e.getKeyCode()==KeyEvent.VK_S) S = false;
		if (e.getKeyCode()==KeyEvent.VK_D) D = false;	
	}

	//Mouse Inputs:
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getButton()==MouseEvent.BUTTON1)
		{
			fire++;
			guns();
			
			M1 = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if (e.getButton()==MouseEvent.BUTTON1) M1 = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	

//UNUSED METHOD
	public void keyTyped(KeyEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}