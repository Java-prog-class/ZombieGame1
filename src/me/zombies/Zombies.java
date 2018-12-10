package me.zombies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")
class Zombies extends Rectangle{

	//Colors
	Color White = new Color (255, 255, 255);
	Color Blue = new Color (0, 0, 255);
	Color Red = new Color (255, 0, 0);
	Color Black = new Color (0, 0, 0);
	BasicStroke ZombiesHPBarStroke = new BasicStroke(Main.WIN/500);

	//Zombies Type
	int GREEN = 1;
	int BLUE = 2;
	int RED = 3;
	int GOLD = 4;

	int ZombiesWidth = Main.WIN/30;
	
//Variables
	int type;
	int vx, vy; //speed
	int r = 20;

	boolean alive;

	int HP, maxHP, damage;
	double PercentRatio, PercentHP, angle;

	Zombies(int type){

		//TODO: Create Zombie Behaviour Here



		// Creating GREEN Zombies
		if (type==GREEN) {					
			this.maxHP= 10;
			this.HP = 10;
			//this.speed = Main.WIN/1500;
			vx = (int)(Math.random()*2) + 8;
			if(Math.random() > 0.5) {
				vx = vx * -1;
			}

			vy = (int)(Math.random()*2) + 8;
			if(Math.random() > 0.5) {
				vy = vy * -1;
			}
			this.damage = 1;
		}

		// Creating BLUE Zombies	
		if (type==BLUE) {					
			this.maxHP= 20;
			this.HP = 20;
			vx = (int)(Math.random()*2) + 6;
			if(Math.random() > 0.5) {
				vx = vx * -1;
			}

			vy = (int)(Math.random()*2) + 6;
			if(Math.random() > 0.5) {
				vy = vy * -1;
			}			
			this.damage = 5;
		}

		// Creating RED Zombies
		if (type==RED) {					
			this.maxHP= 50;
			this.HP = 50;
			vx = (int)(Math.random()*2) + 4;
			if(Math.random() > 0.5) {
				vx = vx * -1;
			}

			vy = (int)(Math.random()*2) + 4;
			if(Math.random() > 0.5) {
				vy = vy * -1;
			}			
			this.damage = 10;
		}

		// Creating GOLD Zombies		
		if (type==GOLD) {					
			this.maxHP= 100;
			this.HP = 100;
			vx = (int)(Math.random()*2) + 2;
			if(Math.random() > 0.5) {
				vx = vx * -1;
			}

			vy = (int)(Math.random()*2) + 2;
			if(Math.random() > 0.5) {
				vy = vy * -1;
			}			
			this.damage = 20;
		}

		this.x = (int)(Math.random()*Main.WIN);
		this.y = (int)(Math.random()*Main.WIN);
		this.width = ZombiesWidth;
		this.height = ZombiesWidth;

		this.type=type;

		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
	}

	void paint(Graphics g, Graphics2D g2) {

		g.setColor(Black);
		g.fillRect(x, y, ZombiesWidth, ZombiesWidth);

		
		if (this.type==GREEN) g.setColor(Color.GREEN);
		if (this.type== BLUE) g.setColor(Color.BLUE);
		if (this.type==  RED) g.setColor(Color.RED);
		if (this.type== GOLD) g.setColor(Color.YELLOW);
	
		
		g.fillOval(x, y, ZombiesWidth, ZombiesWidth);

		drawZombiesHealthBar(g, g2, type);
	}

	void drawZombiesHealthBar (Graphics g, Graphics2D g2, int type) {
		g2.setStroke(ZombiesHPBarStroke);

		int BarWidth = Main.WIN/10; 	// <---- Constant Ratios based off of the Screen Width
		int BarHeight = Main.WIN/150;

		int BarX = x+(ZombiesWidth/2)-(BarWidth/2);
		int BarY = y-(Main.WIN/60);

		g.setColor(White);										// <---- White Background
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
