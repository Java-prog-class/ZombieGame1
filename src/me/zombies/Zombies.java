package me.zombies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
class Zombies extends Rectangle {

	//Colors
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (100, 100, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	BasicStroke ZombiesHPBarStroke = new BasicStroke(Main.WIN/500);

	//Zombies Type
	int GREEN = 1;
	int BLUE = 2;
	int RED = 3;
	int GOLD = 4;

	int ZombiesWidth = 35;
	
	double angle;
	
//Variables
	int type;
	int vx, vy; //speed
	int r = 20;
	int score;
	BufferedImage Img = null;

	boolean alive;
	double PercentRatio;
	double PercentHP;
	int HP, maxHP, damage;
	long lastHit = 0L;
	int direction;

	Zombies(int type) {

	// Creating GREEN Zombies
		if (type==GREEN) {					
			this.maxHP= 10;
			this.HP = 10;
			this.score = 10;
			
			this.damage = 1;
			
		//Load the Sprite
			this.Img = null;
			try { this.Img = ImageIO.read(new File("GreenZ.png"));
			} catch (IOException e) {}
			
			
		}

	// Creating BLUE Zombies	
		if (type==BLUE) {					
			this.maxHP= 20;
			this.HP = 20;
			this.score = 50;		
			this.damage = 5;
			
		//Load the Sprite
			this.Img = null;
			try { this.Img = ImageIO.read(new File("BlueZ.png"));
			} catch (IOException e) {}
		}

	// Creating RED Zombies
		if (type==RED) {					
			this.maxHP= 50;
			this.HP = 50;
			this.score = 75;	
			this.damage = 10;
			
		//Load the Sprite
			this.Img = null;
			try { this.Img = ImageIO.read(new File("RedZ.png"));
			} catch (IOException e) {}
		}

	// Creating GOLD Zombies		
		if (type==GOLD) {					
			this.maxHP= 100;
			this.HP = 100;
			this.score = 100;			
			this.damage = 20;
			
		//Load the Sprite
			this.Img = null;
			try { this.Img = ImageIO.read(new File("GoldZ.png"));
			} catch (IOException e) {}
		}
		
	//Speed
		vx = (int)(Math.random()*2) + 2;
		if(Math.random() > 0.5) {
			vx = vx * -1;
		}

		vy = (int)(Math.random()*2) + 2;
		if(Math.random() > 0.5) {
			vy = vy * -1;
		}

		this.x = (int)(Math.random()*Main.WIN);
		this.y = (int)(Math.random()*Main.WIN);
		this.width = ZombiesWidth;
		this.height = ZombiesWidth;

		this.type=type;

		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
		
		this.angle = Math.atan2(this.vy, this.vx);
	}

	void drawZombiesHealthBar (Graphics g, Graphics2D g2) {
		g2.setStroke(ZombiesHPBarStroke);

		int BarWidth = ZombiesWidth*3; 	// <---- Constant Ratios based off of the Screen Width
		int BarHeight = ZombiesWidth/5;

		int BarX = x+(ZombiesWidth/2)-(BarWidth/2);
		int BarY = y-(Main.WIN/60);

		g.setColor(Black);										// <---- White Background
		g.fillRect(BarX, BarY, BarWidth, BarHeight);


		if (this.type==GREEN) g.setColor(Color.GREEN);			// <---- Colored Health Meter
		if (this.type== BLUE) g.setColor(Color.BLUE);
		if (this.type==  RED) g.setColor(Color.RED);
		if (this.type== GOLD) g.setColor(Color.YELLOW);

		int HPBarWidth = (int) (PercentHP*BarWidth); 			// <---- The Size of the Meter based of the Health Precentage
		g.fillRect(BarX, BarY, HPBarWidth, BarHeight);

		g.setColor(Black);										// <---- Black Boarder
		g.drawRect(BarX, BarY, BarWidth, BarHeight);		

	}

}
