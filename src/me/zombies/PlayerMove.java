package me.zombies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PlayerMove implements KeyListener, MouseListener, MouseMotionListener{

//Global Variables	
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	Font HPBar = new Font("Arial", Font.PLAIN, 45);
	BasicStroke stroke = new BasicStroke(5);
	
	final static int WIN = 1500;
	DrawingPanel drPanel = new DrawingPanel();
	static JFrame window;
	
	static PlayerStats Player = new PlayerStats("Josh");
	static double UP =   0.0,
			    LEFT = 270.0,
			    DOWN = 180.0,
			   RIGHT =  90.0;
	
	static int mouseX;
	static int mouseY;
	
	static boolean W = false, 
				   A = false, 
				   S = false,  
				   D = false,
				  M1 = false,
				  M2 = false;
	
	Timer timer;
	int tSpeed = 1;
	
	public static void main(String[] args) {new PlayerMove();}
	
	PlayerMove() {
	
		window = new JFrame("Player");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		drPanel.addKeyListener(this);
		drPanel.addMouseMotionListener(this);
		window.add(drPanel); 
		window.pack();	
		window.setVisible(true);
		timer = new Timer(tSpeed, new TimerListener());
		timer.start();
	}
	
	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel {
		
		DrawingPanel(){
			this.setPreferredSize(new Dimension (WIN, WIN));
			this.setBackground(White);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;	
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			super.paintComponent(g);
			this.requestFocus();
			
		//Draw the Player
			BufferedImage img = null;
			try { img = ImageIO.read(new File("Player.png"));
			} catch (IOException e) {}
	
			g2.rotate(Math.toRadians(Player.angle), Player.x+38, Player.y+50);		
			g.drawImage(img, Player.x, Player.y, 76, 100, drPanel);
			g2.rotate(Math.toRadians(-Player.angle), Player.x+38, Player.y+50);
			
			
		//Draw the Health Bar
			g2.setStroke(stroke);
			int HPBarWidth = (int) (Player.PercentHP*500);
			
			g.setColor(White);					// <---- White Background
			g.fillRect(50, 50, 500, 50);
			
			g.setColor(Red);					// <---- Red Meter
			g.fillRect(50, 50, HPBarWidth, 50);
			
			g.setColor(Black);					// <---- Black Boarder
			g.drawRect(50, 50, 500, 50);
			
			g2.setFont(HPBar);
			String HPString = Player.HP+"/"+Player.maxHP;
			g.drawString(HPString, 430, 95);
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
			if (W && Player.y>=0) Player.y-=Player.speed;
			if (A && Player.x>=0) Player.x-=Player.speed;
			if (S && Player.y<=WIN-100) Player.y+=Player.speed;
			if (D && Player.x<=WIN-100) Player.x+=Player.speed;
			
		//Rotation
			int deltaX = mouseX-Player.x;
			int deltaY = mouseY-Player.y;
			Player.angle = Math.toDegrees(Math.atan2(deltaX, -deltaY));
			
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