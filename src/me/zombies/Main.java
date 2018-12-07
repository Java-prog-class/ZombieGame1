package me.zombies;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;		// <---- Imports	
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import maps.ForestMapTest;
import props.Building;

import javax.imageio.ImageIO;
import javax.swing.*;



public class Main implements KeyListener, MouseListener, MouseMotionListener{

	//JFrame and JWindow Creations
	final static int WIN = 650;
	static JFrame window;
	DrawingPanel drPanel = new DrawingPanel();


	//Colors, Font, and Stroke 
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
	HospitalMap1  hospitalMap1 = new HospitalMap1();
	
	//Global Variables:
	static PlayerStats Player = new PlayerStats("Josh");	// <---- Creating the Player Object
	

	//Varibles for shooting
	//Array list for bullets

	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	//Constants for Zombie Types
	int GreenZombie = 1;
	int BlueZombie =  2;
	int RedZombie =   3;
	int GoldZombie =  4;

	//Global Variables
	PlayerStats Player = new PlayerStats("Josh");	// <---- Creating the Player Object

	ArrayList<Zombies> zombies = new ArrayList<Zombies>();
	int Round = 0;
	int ZombiesCounter = 0;
	Zombies z;

	//Rotation of Player
	int deltaX = mouseX-Player.x; 									// <---- Subtracting the Player location from the Mouse Location
	int deltaY = mouseY-Player.y;

	//Weapons
	Weapon pistol = new Weapon(5, 7, "Pistol");
	int fire;


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

	public static void main (String [] args) {new Main();}

	Main() {	
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
		window.setVisible(true);								// <---- Sets it visable

		timer = new Timer(tSpeed, new TimerListener());			// <---- Creates the Timer
		timer.start();											// <---- Starts the Timer

	}

	private void guns() {
		int ammo = fire;
		
		if(fire>0) {
			System.out.println(fire);
			if(ammo<pistol.ammo) {
				bullets.add(new Bullet(Player));
				ammo--;
			}else {
				
				System.out.println("no ammo");
			}
		}

	}

	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setPreferredSize(new Dimension (WIN, WIN));	// <---- Sets the Size
			this.setBackground(Grass);							// <---- Sets the background color
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;	
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			super.paintComponent(g);
			this.requestFocus();
			
			//Draws all the Props:
			//These must be called first otherwise they draw over the UI and Player:
			
			for (Floor f : hospitalMap1.floors)
			{
				f.paint(g);
			drawPlayer(g, g2);
			moveBullets();
			addBullets(g);

			//Draw Zombies
			for (Zombies z: zombies) {
				z.paint(g, g2);
			}


			
			for (Wall w : hospitalMap1.walls)
			{
				w.paint(g);
			}
			
			g2.setStroke(stroke);
			this.requestFocus();
			
			//Draws the Player:
			BufferedImage img = null;
			try { img = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
			} catch (IOException e) {}

			g2.rotate(Math.toRadians(Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));		// <---- Rotates the whole screen	
			g.drawImage(img, Player.x, Player.y, Player.width, Player.height, drPanel);							// <---- Draws the Player
			g2.rotate(Math.toRadians(-Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));	// <---- Rotates the whole screen back


			//Draw the Health Bar:
			int BarWidth = WIN/3; 	// <---- Constant Ratios based off of the Screen Width
			int BarHeight = WIN/30;

			g.setColor(White);										// <---- White Background
			g.fillRect(BarHeight, BarHeight, BarWidth, BarHeight);

			g.setColor(Red);										// <---- Red Health Meter
			int HPBarWidth = (int) (Player.PercentHP*BarWidth); 	// <---- The Size of the Meter based of the Health Precentage
			g.fillRect(BarHeight, BarHeight, HPBarWidth, BarHeight);

			g.setColor(Black);										// <---- Black Boarder
			g.drawRect(BarHeight, BarHeight, BarWidth, BarHeight);

			g2.setFont(HPBarFont);										// <---- Display Text of HP
			String HPString = Player.HP+"/"+Player.maxHP;
			g.drawString(HPString, (int)(WIN/3.6), WIN/16);

		}		
	}


	//Draw the Player
	void drawPlayer(Graphics g, Graphics2D g2) {

		BufferedImage PlayerImg = null;
		try { PlayerImg = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
		} catch (IOException e) {}

		g2.rotate(Math.toRadians(Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));		// <---- Rotates the whole screen	
		g.drawImage(PlayerImg, Player.x, Player.y,Player.width, Player.height, drPanel);					// <---- Draws the Player
		g2.rotate(Math.toRadians(-Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));	// <---- Rotates the whole screen back

	}

	//Move the Player
	void movePlayer() {

		double vx;
		double vy;
		double moveAngle;

		if (W) {								// <---- Moving Forward

			moveAngle = Math.toRadians(Player.angle-90);

			vx = Player.speed*Math.cos(moveAngle);	// <----Gets the horizontal speed based off the angle
			vy = Player.speed*Math.sin(moveAngle);	// <----Gets the  vertical  speed based off the angle

			Player.y+=vy; 	// <---- Moves the player in accordance with
			Player.x+=vx;	//		 it's vertical and horizontal speeds
		}

		if (S) {								// <---- Moving Back

			moveAngle = Math.toRadians(Player.angle+90);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;
		}

		if (D) {								// <---- Circling Right (Counter Clockwise)

			moveAngle = Math.toRadians(Player.angle);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;

		}

		if (A) {								// <---- Circling Left (Clockwise)

			moveAngle = Math.toRadians(Player.angle-180);

			vx = Player.speed*Math.cos(moveAngle);
			vy = Player.speed*Math.sin(moveAngle);

			Player.y+=vy;
			Player.x+=vx;
		}	

	}

	//Draw the Health Bar
	void drawPlayerHealthBar(Graphics g, Graphics2D g2) {

		int BarWidth = WIN/3; 	// <---- Constant Ratios based off of the Screen Width
		int BarHeight = WIN/30;

		g.setColor(White);										// <---- White Background
		g.fillRect(BarHeight, BarHeight, BarWidth, BarHeight);

		g.setColor(Red);										// <---- Red Health Meter
		int HPBarWidth = (int) (Player.PercentHP*BarWidth); 	// <---- The Size of the Meter based of the Health Precentage
		g.fillRect(BarHeight, BarHeight, HPBarWidth, BarHeight);

		g.setColor(Black);										// <---- Black Boarder
		g.drawRect(BarHeight, BarHeight, BarWidth, BarHeight);

		g2.setFont(HPBarFont);									// <---- Display Text of HP
		String HPString = Player.HP+"/"+Player.maxHP;
		g.drawString(HPString, (int)(WIN/3.6), WIN/16);
	}

	//Draw Bullets
	void addBullets(Graphics g) {
		for(Bullet c : bullets) {
			c.paint(g);
		}
	}
	void drawZombieCounter(Graphics g, Graphics2D g2) {

		FontMetrics fontMetrics = g2.getFontMetrics(ZombiesCounterFont);	// <---- Creates a FontMetrics

		String str = null;									// <---- Initizalises the string

		if (ZombiesCounter<10) str = "0"+ZombiesCounter;  	// <---- Puts a '0' in front of single-digit numers
		else str = ""+ZombiesCounter;

		g.setColor(Black);			
		g2.setFont(ZombiesCounterFont);			// <---- Changes the font

		int rightAlign = (WIN/30)*29; 			// <---- Creates a variable based off the screen width to align the text to the right

		g.drawString(str, (rightAlign - fontMetrics.stringWidth(str)), WIN/16);

	}

	//Add Zombies
	void addZombies() {
		Round++;
		ZombiesCounter = 0;

		//Round 1 Zombie Adding
		if (Round==1) {
			for (int i=0; i<45; i++) zombies.add(new Zombies(GreenZombie));
			ZombiesCounter = 45;
		}

		//Round 2 Zombie Adding
		if (Round==2) {
			for (int i=0; i<35; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 45;
		}

		//Round 3 Zombie Adding
		if (Round==3) {
			for (int i=0; i<40; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<20; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 60;
		}
		//Round 4 Zombie Adding
		if (Round==4) {
			for (int i=0; i<30; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<35; i++) zombies.add(new Zombies(BlueZombie));
			ZombiesCounter = 65;
		}

		//Round 5 Zombie Adding
		if (Round==5) {
			for (int i=0; i<35; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<25; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 1; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 61;
		}

		//Round 6 Zombie Adding
		if (Round==6) {
			for (int i=0; i<40; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<30; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i< 5; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 75;
		}			

		//Round 7 Zombie Adding
		if (Round==7) {
			for (int i=0; i<45; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<25; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<15; i++) zombies.add(new Zombies(RedZombie));
			ZombiesCounter = 85;
		}

		//Round 8 Zombie Adding
		if (Round==8) {
			for (int i=0; i<25; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<05; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i<01; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 41;
		}

		//Round 9 Zombie Adding
		if (Round==9) {
			for (int i=0; i<35; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<25; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<15; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i<05; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 80;
		}

		//Round 10 Zombie Adding
		if (Round==10) {
			for (int i=0; i<50; i++) zombies.add(new Zombies(GreenZombie));
			for (int i=0; i<35; i++) zombies.add(new Zombies(BlueZombie));
			for (int i=0; i<25; i++) zombies.add(new Zombies(RedZombie));
			for (int i=0; i<10; i++) zombies.add(new Zombies(GoldZombie));
			ZombiesCounter = 120;
		}

	}

	//Move Bullets
	private void moveBullets() {
		//move
		for (Bullet b : bullets) {
			b.move();
		}

		//remove circles when they're off the screen		
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);	
			if (b.x > WIN) {
				bullets.remove(i);
				i--;	//  Important! Add this in.
			}
		}	
	}

	//Player Inputs

	//Keyboard Presses

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

	private class TimerListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{

			// ----- Stuff that happens every frame of the game -----

			//Movement:
			if (W && Player.y >= 0) Player.y -= Player.speed;					// <---- Moving Up
			if (A && Player.x >= 0) Player.x -= Player.speed;					// <---- Moving Left
			if (S && Player.y <= WIN-Player.height) Player.y += Player.speed;	// <---- Moving Down
			if (D && Player.x <= WIN-Player.height) Player.x += Player.speed;	// <---- Moving Right

			//Collision:
			Rectangle player = new Rectangle(Player.x, Player.y, Player.width, Player.height);
			
			//Walls:
			for (Wall w : hospitalMap1.walls) 
			{
				if (player.intersects(w)) 
				{
					if (Player.width <= w.height && D)
					{ 
						Player.x -= 5;
					}
					
					if (Player.width <= w.height && A)
					{
						Player.x += 5;
					}
					
					if (Player.height <= w.width && S)
					{
						Player.y -= 5;
					}
					
					if (Player.height <= w.width && W)
					{
						Player.y += 5;
					}
 				}
			}
			
			/*
			for (River r : forestMapTest.rivers) 
			{
				if (player.intersects(r)) 
				{
					//Left Side of the River:
					if (Player.width <= r.height && D)
					{ 
						Player.x -= 5;
					}
					
					//Right Side of the Building:
					if (Player.width <= r.height && A)
					{
						Player.x += 5;
					}
					
					//Top of the Building:
					if (Player.height <= r.width && S)
					{
						Player.y -= 5;
					}
					
					//Bottom of the Building:
					if (Player.height <= r.width && W)
					{
						Player.y += 5;
					}
 				}
			}
			
			for (Bridge b: forestMapTest.bridges)
			{
				if (player.intersects(b))
				{
					//Top of the Building:
					if (Player.height <= b.width && S)
					{
						Player.y -= 5;
					}
					
					//Bottom of the Building:
					if (Player.height <= b.width && W)
					{
						Player.y += 5;
					}	
				}
			}*/
			
			//Rotation:
			int deltaX = mouseX-Player.x; 									// <---- Subtracting the Player location from the Mouse Location
			int deltaY = mouseY-Player.y;
			Player.angle = Math.toDegrees(Math.atan2(deltaX, -deltaY)); 	// <---- The angle of rotation

			//Death Check:
			if (Player.HP<=0) Player.alive = false;


			// ------------------------------------------------------
			// ----- Stuff that happens every frame of the game -----
			// ------------------------------------------------------	

			movePlayer();

			Player.angle = Math.toDegrees(Math.atan2(deltaY, deltaX))+90; 	// <---- The angle of rotation

			//Player Death Check
			if (Player.HP<=0) Player.alive = false;

			//Zombie Death Check
			for (Zombies z: zombies) {
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


	public void mousePressed(MouseEvent e) {
		M1 = true;
		fire++;
		guns();
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

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	//UNUSED METHOD
	public void keyTyped(KeyEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}