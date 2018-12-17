package me.zombies;

import java.awt.BasicStroke;		// <---- Imports	
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main implements KeyListener, MouseListener, MouseMotionListener {

	//JFrame and JWindow Creations
	final static int WIN = 700;
	static JFrame window;
	DrawingPanel drPanel = new DrawingPanel();

	//Colors, Font, and Stroke 
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	Font HPBar = new Font("Arial", Font.PLAIN, WIN/30);
	Font HPBarFont = new Font("Arial", Font.PLAIN, WIN/30);
	Font ZombiesCounterFont = new Font("Arial", Font.BOLD, WIN/15);
	BasicStroke stroke = new BasicStroke(WIN/300);

	//Variables for shooting
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();	// <---- Array list for bullets

	//Constants for Zombie Types
	int GreenZombie = 1;
	int BlueZombie =  2;
	int RedZombie =   3;
	int GoldZombie =  4;

	//Global Variables
	int Round = 5;
	PlayerStats player = new PlayerStats("Josh", Round);			// <---- Creating the Player Object

	//Zombies
	ArrayList<Zombies> zombies = new ArrayList<Zombies>();
	int ZombiesCounter = 0;
	Zombies z;

	//Rotation of Player
	int deltaX = mouseX-player.x; 	// <---- Subtracting the Player location from the Mouse Location
	int deltaY = mouseY-player.y;

	//Weapons
	Weapon weapon;
	int fire;
	int magX, magY;	
	static boolean One = false;
	String str = null;

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

	public static void main (String [] args) {new Main();}

	Main() {
		weapon = new Pistol();
		setupGUI();

		timer = new Timer(tSpeed, new TimerListener());			// <---- Creates the Timer
		timer.start();											// <---- Starts the Timer

	}

	void setupGUI() { 
		//JFrame Setup:
		window = new JFrame("Zombie Game");						// <---- Sets the titles
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// <---- Allows for closing
		window.setResizable(false);								// <---- Turns of resizing
		drPanel.addKeyListener(this);							// <---- Adds the keyboard listener to the drPanel
		drPanel.addMouseListener(this);							// <---- Adds the mouse listener to the drPanel
		drPanel.addMouseMotionListener(this);					// <---- Adds the mouse motion listener to the drPanel
		window.add(drPanel);									// <---- Adds the drPanel to the Window
		window.pack();											// <---- Packs the Window
		window.setVisible(true);								// <---- Sets it visable

	}

	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setPreferredSize(new Dimension (WIN, WIN));	// <---- Sets the Size
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;	
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			super.paintComponent(g);
			this.requestFocus();

			drawPlayer(g2);
			drawBullets(g);
			drawZombies(g, g2);
			drawPlayerHealthBar(g, g2);
			drawAmmoCounter(g, g2);
			drawZombieCounter(g, g2);

		}		
	}


	//Draw the Player
	void drawPlayer(Graphics2D g2) {

		BufferedImage PlayerImg = null;
		try { PlayerImg = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
		} catch (IOException e) {}

		g2.rotate(Math.toRadians(player.angle), player.x+(player.width/2), player.y+(player.height/2));		// <---- Rotates the whole screen	
		g2.drawImage(PlayerImg, player.x, player.y,player.width, player.height, drPanel);					// <---- Draws the Player
		g2.rotate(Math.toRadians(-player.angle), player.x+(player.width/2), player.y+(player.height/2));	// <---- Rotates the whole screen back

	}

	//Move the Player
	void movePlayer() {

		double vx;
		double vy;
		double moveAngle;

		if (W) {								// <---- Moving Forward

			moveAngle = Math.toRadians(player.angle-90);

			vx = player.speed*Math.cos(moveAngle);	// <----Gets the horizontal speed based off the angle
			vy = player.speed*Math.sin(moveAngle);	// <----Gets the  vertical  speed based off the angle

			player.y+=vy; 	// <---- Moves the player in accordance with
			player.x+=vx;	//		 it's vertical and horizontal speeds
		}


		if (S) {								// <---- Moving Back

			moveAngle = Math.toRadians(player.angle+90);

			vx = player.speed*Math.cos(moveAngle);
			vy = player.speed*Math.sin(moveAngle);

			player.y+=vy;
			player.x+=vx;
		}

		if (D) {								// <---- Circling Right (Counter Clockwise)

			moveAngle = Math.toRadians(player.angle);

			vx = player.speed*Math.cos(moveAngle);
			vy = player.speed*Math.sin(moveAngle);

			player.y+=vy;
			player.x+=vx;

		}

		if (A) {								// <---- Circling Left (Clockwise)

			moveAngle = Math.toRadians(player.angle-180);

			vx = player.speed*Math.cos(moveAngle);
			vy = player.speed*Math.sin(moveAngle);

			player.y+=vy;
			player.x+=vx;
		}	

	}

	//Draw the Player Health Bar
	void drawPlayerHealthBar(Graphics g, Graphics2D g2) {

		int BarWidth = WIN/3; 	// <---- Constant Ratios based off of the Screen Width
		int BarHeight = WIN/30;

		g.setColor(White);										// <---- White Background
		g.fillRect(BarHeight, BarHeight, BarWidth, BarHeight);

		g.setColor(Red);										// <---- Red Health Meter
		int HPBarWidth = (int) (player.PercentHP*BarWidth); 	// <---- The Size of the Meter based of the Health Precentage
		g.fillRect(BarHeight, BarHeight, HPBarWidth, BarHeight);

		g.setColor(Black);										// <---- Black Boarder
		g.drawRect(BarHeight, BarHeight, BarWidth, BarHeight);

		g2.setFont(HPBarFont);									// <---- Display Text of HP
		String HPString = player.HP+"/"+player.maxHP;
		g.drawString(HPString, (int)(WIN/3.6), WIN/16);
	}

	//Draw the Zombies
	void drawZombies(Graphics g, Graphics2D g2) {

		for (Zombies z: zombies) {
			//g2.rotate(z.angle);
			//AffineTransform tx = AffineTransform.getRotateInstance(z.angle);
			//AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			//g2.drawImage(op.filter(z.Img, null), z.x, z.y, null);
			//g2.rotate(z.angle, z.x+(z.width/2), z.y+(z.height/2));
			//g.drawImage(z.Img, z.x, z.y, z.ZombiesWidth, z.ZombiesWidth, drPanel);
			//g.drawImage(op.filter(z.Img, tmp), z.x, z.y, z.ZombiesWidth, z.ZombiesWidth, drPanel);
			//g2.drawImage(z.Img, z.x, z.y, z.ZombiesWidth, z.ZombiesWidth, null);
			//g2.rotate(-z.angle, z.x+(z.width/2), z.y+(z.height/2));
			//g2.rotate(z.angle * -1);

			g2.rotate(z.angle, z.x+(z.ZombiesWidth/2), z.y+(z.ZombiesWidth/2));	
			g2.drawImage(z.Img, z.x, z.y,z.ZombiesWidth, z.ZombiesWidth, drPanel);				
			g2.rotate(-z.angle, z.x+(z.ZombiesWidth/2), z.y+(z.ZombiesWidth/2));

			z.drawZombiesHealthBar(g, g2);	
		}

	}

	void checkForFire() {

	}

	void switchWeapon() {
		player.currentWeapon++;
		switch(player.currentWeapon) {
		case 0:
			weapon = new Pistol();
			break;
		case 1:
			//weapon = new Shotgun();
			break;
		case 2:
			player.currentWeapon = 0;
			weapon = new Pistol();
		}
	}
	//Guns
	private void pistol() {
		
		if(weapon.ammo>0) {
			System.out.println("yo");
			bullets.add(new Bullet(player));
			weapon.ammo--; 	
		}

		if(weapon.reload) {
			weapon.reload = false;
			str = "0"+ weapon.ammo;
		}	
	}

	//Draw Bullets
	void drawBullets(Graphics g) {
		for(Bullet c : bullets) {
			c.paint(g);
		}
	}

	//Draw Ammo Counter
	void drawAmmoCounter(Graphics g, Graphics2D g2) {

		if (weapon.ammo<10) str = "0"+weapon.ammo;  	// <---- Puts a '0' in front of single-digit numers)

		g.setColor(Black);			
		g2.setFont(ZombiesCounterFont);

		g.drawString(str, WIN-WIN+10, WIN-10);
	}

	//Draw the Zombie Counter
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

	//Add Zombies (ROUNDS)
	void addZombies() {
		Round++;
		ZombiesCounter = 0;

		//Round 1 Zombie Adding
		if (Round==1) {
			for (int i=0; i<5; i++) zombies.add(new Zombies(GreenZombie));
			ZombiesCounter = 5;
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

	//Move Bullets
	private void moveBullets() {

		//Move
		for (Bullet b : bullets) {
			b.move();
		}

		//Remove Circles when they're off the screen		
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);	
			if (b.x > WIN) {
				bullets.remove(i);
				i--;	//  Important! Add this in.
			}
		}	
	}

	//Moving Zombies
	private void moveZombies() {
		for(Zombies z : zombies) {
			z.x += z.vx;
			z.y += z.vy;

			//Bouncing off the walls
			if (z.x-z.r < 0) z.vx *= -1;
			if (z.y-z.r < 0) z.vy *= -1;
			if (z.x+z.r > WIN) z.vx *= -1;
			if (z.y+z.r > WIN) z.vy *= -1;
			z.angle = Math.atan2(z.vy, z.vx); 
		}
	}

	//Timer
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// ------------------------------------------------------		
			// ----- Stuff that happens every frame of the game -----
			// ------------------------------------------------------

			//Fixes the Player's Health Bar
			player.PercentRatio = ((player.HP*100)/player.maxHP);
			player.PercentHP = player.PercentRatio/100;

			//Fixes the Zombies' Health Bar
			for (Zombies z: zombies) {
				z.PercentRatio = ((z.HP*100)/z.maxHP);
				z.PercentHP = z.PercentRatio/100;
			}

			movePlayer();

			moveZombies();

			moveBullets();

			//Rotation of Player
			int deltaX = mouseX-player.x; 									// <---- Subtracting the Player location from the Mouse Location
			int deltaY = mouseY-player.y;
			player.angle = Math.toDegrees(Math.atan2(deltaY, deltaX))+90; 	// <---- The angle of rotation

			//Player Death Check
			if (player.HP<=0) player.alive = false;

			//Zombie Hit Player Check
			for (Zombies z: zombies) {
				if (z.intersects(player)) {
					long now = System.currentTimeMillis();
					if ((now-z.lastHit)>1000) {
						z.lastHit = now;
						player.HP-=z.damage;
					}
				}
			}

			//Bullet Hit Zombie Check
			for (Zombies z: zombies) {
				for (int j=0; j<bullets.size(); j++) {
					Bullet b = bullets.get(j);
					if (z.intersects(b)) {
						if(One) {
							z.HP-= weapon.dmg;
						}
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

	// -------------------------
	// ----- Player Inputs -----
	// -------------------------

	//Keyboard Presses

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode()==KeyEvent.VK_W) W = true;
		if (e.getKeyCode()==KeyEvent.VK_A) A = true;
		if (e.getKeyCode()==KeyEvent.VK_S) S = true;
		if (e.getKeyCode()==KeyEvent.VK_D) D = true;	
		if (e.getKeyCode()==KeyEvent.VK_R) weapon.reload = true;
		//if (e.getKeyCode()==KeyEvent.VK_I) One = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode()==KeyEvent.VK_W) W = false;
		if (e.getKeyCode()==KeyEvent.VK_A) A = false;
		if (e.getKeyCode()==KeyEvent.VK_S) S = false;
		if (e.getKeyCode()==KeyEvent.VK_D) D = false;	
		if (e.getKeyCode()==KeyEvent.VK_I) switchWeapon();

	}	

	//Mouse Inputs

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton()==MouseEvent.BUTTON1) {
			fire++;
			pistol();
			M1 = true;
		}


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
	public void keyTyped(KeyEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent e) {}
}