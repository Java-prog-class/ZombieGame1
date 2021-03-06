package me.zombies;

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

@SuppressWarnings("serial")
public class Main extends Rectangle implements KeyListener, MouseListener, MouseMotionListener{

//JFrame and JWindow Creations
	final static int WIN = 1500;
	static JFrame window;
	DrawingPanel drPanel = new DrawingPanel();

//Colors, Font, and Stroke 
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Green = new Color(0, 255, 0);
	Color Black = new Color (0, 0, 0);
	Font HPBar = new Font("Arial", Font.PLAIN, WIN/30);
	Font HPBarFont = new Font("Arial", Font.PLAIN, WIN/30);
	Font ZombiesCounterFont = new Font("Arial", Font.BOLD, WIN/15);
	Font GameOverFont = new Font("Arial", Font.BOLD, (int)(WIN/7.5));
	Font CountdownFont = new Font("Arial", Font.BOLD, 500);
	BasicStroke stroke = new BasicStroke(WIN/300);
	
//Variables for start button rectangle
	int buttonWidth = (WIN/15)*6;
	int buttonHeight = WIN/12;
	int buttonX = (WIN/2)-(buttonWidth/2);
	int buttonY = (WIN/5)*4-(buttonHeight/2);
	boolean buttonHovering = false;
	Color startButtonColor = Red;
	Color startButtonTextColor = White;
	
//Variables for shooting
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();	// <---- Array list for bullets

//Constants for Zombie Types
	int GreenZombie = 1;
	int BlueZombie =  2;
	int RedZombie =   3;
	int GoldZombie =  4;

//Global Variables
	PlayerStats Player = new PlayerStats("Josh");			// <---- Creating the Player Object
	int Score = 0;
	int Round = 3;
	int Countdown = 5;
	long gameStartTime = 0L;
	
//Zombies
	ArrayList<Zombies> zombies = new ArrayList<Zombies>();
	int ZombiesCounter = 0;
	Zombies z;

//Rotation of Player
	int deltaX = mouseX-Player.x; 	// <---- Subtracting the Player location from the Mouse Location
	int deltaY = mouseY-Player.y;

//Weapons
	Weapon pistol = new Weapon(5, 6, "Pistol");
	int fire;
	int magX, magY;
	int ammo = pistol.ammo-fire;
	static boolean R = false;
	String str = null;

//Mouse Variables	
	static int mouseX;	
	static int mouseY;

//Inputer variables for the Player
	static boolean W = false,	// <---- These variables are set to false,
				   A = false,	//		 when the key is pressed or mouse
				   S = false,	//       button is clicked, the corresponding
				   D = false,	//		  variable is set to true
				  M1 = false,			
				  M2 = false;

	Timer gameTimer;	// <---- Initializes the Timers
	Timer deathTimer;
	Timer startScreenTimer;
	int tSpeed = 1;		// <---- The Timer's Speed
	
	long lastHit = 0L, deathTime = 0L;
	boolean showGameOverScreen = false;
	boolean showStartScreen = true;
	boolean showCountdown = false;

	public static void main (String [] args) {new Main();}

	Main() {	
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

//		Magazine();
		
		gameTimer = new Timer(tSpeed, new GameTimerListener());					// <---- Creates the Main Game Timer
		deathTimer = new Timer(tSpeed, new DeathTimerListener());				// <---- Creates the Death Timer
		startScreenTimer = new Timer(tSpeed, new StartScreenTimerListener());	// <---- Creates the Start Screen Timer
		
		startScreenTimer.start();		// <---- Starts the Start Screen Timer

	}

//Drawing Panel
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
		
			if (!showStartScreen) {
//				drawMagazine(g);
				drawPlayer(g2);
				addBullets(g);
				drawZombies(g, g2);
				drawPlayerHealthBar(g, g2);
				drawZombieCounter(g, g2);
			}
			
			if (showCountdown) {
				String str;
				if (Countdown == 0) {
					str = "GO";
				}
				else str = ""+Countdown;
				
				FontMetrics fontMetrics = g2.getFontMetrics(CountdownFont);
				int StringWidth = fontMetrics.stringWidth(str);
				int StringX = (WIN/2)-(StringWidth/2);
				int StringY = (WIN/2);
				
				g.setFont(CountdownFont);
				g.setColor(Blue);
				g.drawString(str, StringX, StringY);
				
			}
			
			if (!Player.alive) {
				playerDied(g, g2);
			}
			
			if (showStartScreen) {
				drawStartScreen(g, g2);
			}
			
			if (showGameOverScreen) {
				drawGameOverScreen(g, g2);
			}
		}		
	}

//Draw the Player
	void drawPlayer(Graphics2D g2) {

		BufferedImage PlayerImg = null;
		try { PlayerImg = ImageIO.read(new File("Player.png")); 	// <---- Loads the player Sprite file
		} catch (IOException e) {}

		g2.rotate(Math.toRadians(Player.angle), Player.x+(Player.width/2), Player.y+(Player.height/2));		// <---- Rotates the whole screen	
		g2.drawImage(PlayerImg, Player.x, Player.y,Player.width, Player.height, drPanel);					// <---- Draws the Player
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

//Draw the Player Health Bar
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

//Guns
	private void guns() {
		
		if(fire>=0) {
			if(ammo>0) {
				if(ammo<=pistol.ammo) {
					bullets.add(new Bullet(Player));
					ammo--;
				} 	
			}
		}
		
		if(R) {
			ammo = pistol.ammo;
			R = false;
			str = "0"+ammo;
		}

	}

//Draw Bullets
	void addBullets(Graphics g) {
		for(Bullet c : bullets) {
			c.paint(g);
		}
	}

//Draw Ammo Counter
	void drawAmmoCounter(Graphics g, Graphics2D g2) {

		if (ammo<10) str = "0"+ammo;  	// <---- Puts a '0' in front of single-digit numers)

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

//Start Screen
	private void drawStartScreen(Graphics g, Graphics2D g2) {
		
	//Load the background image
		BufferedImage BackgroundImg = null;
		try { BackgroundImg = ImageIO.read(new File("Start Screen.png"));
		} catch (IOException e) {}
		g2.drawImage(BackgroundImg, 0, 0, WIN, WIN, drPanel);
		
	//Change the button colors according to the mouse position
		if (buttonHovering) {
			startButtonColor = Green;
			startButtonTextColor = Black;
		}
		else {
			startButtonColor = Blue;
			startButtonTextColor = White;
		}
		
	//Draw the Button Rectangle
		g.setColor(startButtonColor);
		g.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
		
	//Draw the Button's Text
		FontMetrics fontMetrics = g2.getFontMetrics(ZombiesCounterFont);
		String ButtonMessage = "Start Game";
		int stringX = (WIN/2)-(fontMetrics.stringWidth(ButtonMessage)/2);
		int stringY = buttonY+fontMetrics.getAscent();
		g2.setFont(ZombiesCounterFont);
		g.setColor(startButtonTextColor);
		g.drawString("Start Game", stringX, stringY);
		
	}

//Death animation
	private void playerDied(Graphics g, Graphics2D g2) {
		FontMetrics fontMetrics = g2.getFontMetrics(GameOverFont);
		String str = "YOU DIED";
		
		int stringWidth = fontMetrics.stringWidth(str);
		int stringHeight = fontMetrics.getAscent();
		int stringX = (WIN/2) - (stringWidth/2);
		int stringY = (WIN/2) - (stringHeight/2);
		
		g.setFont(GameOverFont);
		g.setColor(Red);
		g.drawString(str, stringX, stringY);
		
	}

//Game Over Screen
	private void drawGameOverScreen(Graphics g, Graphics2D g2) {
		FontMetrics fontMetrics = g2.getFontMetrics(GameOverFont);
		
		
		String str1 = "FINAL SCORE:";
		String str2 = ""+Score;
		
		int string1Width = fontMetrics.stringWidth(str1);		
		int string2Width = fontMetrics.stringWidth(str2);
		
		int string1X = (WIN/2) - (string1Width/2);
		int string2X = (WIN/2) - (string2Width/2);
		
		int string1Y = (int)(WIN/7.5);
		int string2Y = WIN/3;
		
		g.setColor(Black);
		g.fillRect(0, 0, WIN, WIN);
		
		g.setColor(Red);
		g.setFont(GameOverFont);
		
		g.drawString(str1, string1X, string1Y);
		g.drawString(str2, string2X, string2Y);
	}
	
//Start Screen Timer
	private class StartScreenTimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		//Check for mouse over the button
			if (mouseX>buttonX && 
				mouseX<buttonX+buttonWidth && 
				mouseY>buttonY &&
				mouseY<buttonY+buttonHeight) {
					buttonHovering = true;
			}
			else buttonHovering = false;
			
		//Check for Button Click
			if (buttonHovering && M1) {
				showStartScreen = false;
				gameStartTime = System.currentTimeMillis();
				showCountdown = true;
			}
		
		//Game Countdown
			if (!showStartScreen) {
				if (System.currentTimeMillis()>=gameStartTime+1000) Countdown = 4;
				if (System.currentTimeMillis()>=gameStartTime+2000) Countdown = 3;
				if (System.currentTimeMillis()>=gameStartTime+3000) Countdown = 2;
				if (System.currentTimeMillis()>=gameStartTime+4000) Countdown = 1;
				if (System.currentTimeMillis()>=gameStartTime+5000) Countdown = 0;
				if (System.currentTimeMillis()>=gameStartTime+6000) {
					showCountdown = false;
					gameTimer.start();
					startScreenTimer.stop();
				}
			}
			

			window.repaint();
		}
	}

//Game Timer
	private class GameTimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		// ------------------------------------------------------		
		// ----- Stuff that happens every frame of the game -----
		// ------------------------------------------------------

		//Fixes the Player's Health Bar
			Player.PercentRatio = ((Player.HP*100)/Player.maxHP);
			Player.PercentHP = Player.PercentRatio/100;

		//Fixes the Zombies' Health Bar
			for (Zombies z: zombies) {
				z.PercentRatio = ((z.HP*100)/z.maxHP);
				z.PercentHP = z.PercentRatio/100;
			}

			movePlayer();
			moveZombies();
			moveBullets();

		//Rotation of Player
			int deltaX = mouseX-Player.x; 									// <---- Subtracting the Player location from the Mouse Location
			int deltaY = mouseY-Player.y;
			Player.angle = Math.toDegrees(Math.atan2(deltaY, deltaX))+90; 	// <---- The angle of rotation
		
		//Check for shooting
			if (M1) {
				fire++;
				guns();
			}
			
		//Player Death Check
			if (Player.HP<=0) {
				Player.alive = false;
				deathTime = System.currentTimeMillis();
				deathTimer.start();
				gameTimer.stop();
			}
			
		//Zombie Hit Player Check
			for (Zombies z: zombies) {
				if (z.intersects(Player)) {
					long now = System.currentTimeMillis();
					if ((now-z.lastHit)>1000) {
						z.lastHit = now;
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
						Score+=z.score;
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

//Game Over Timer
	private class DeathTimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			long now = System.currentTimeMillis();
			
			if (now>=deathTime+2000) showGameOverScreen = true;
			
			moveZombies();
			moveBullets();
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
		if (e.getKeyCode()==KeyEvent.VK_R) R = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode()==KeyEvent.VK_W) W = false;
		if (e.getKeyCode()==KeyEvent.VK_A) A = false;
		if (e.getKeyCode()==KeyEvent.VK_S) S = false;
		if (e.getKeyCode()==KeyEvent.VK_D) D = false;	

	}	

//Mouse Inputs

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

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
//UNUSED METHOD
	public void keyTyped(KeyEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
}