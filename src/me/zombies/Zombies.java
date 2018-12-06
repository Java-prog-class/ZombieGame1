package me.zombies;

import java.awt.Color;
import java.awt.Graphics;

class Zombies {

	int GREEN = 1;
	int BLUE = 2;
	int RED = 3;
	int GOLD = 4;
	
//Variables
	int x, y;
	
	int width, height;
	int type;
	
	boolean alive;
	
	int HP, maxHP, speed, damage;
	double PercentRatio, PercentHP, angle;
	
	Zombies(int type){
		
	//TODO: Create Zombie Behaviour Here
		
		

	// Creating GREEN Zombies
		if (type==GREEN) {					
			this.maxHP= 10;
			this.HP = 10;
			this.speed = Main.WIN/1500;
			this.damage = 1;
		}
		
	// Creating BLUE Zombies	
		if (type==BLUE) {					
			this.maxHP= 20;
			this.HP = 20;
			this.speed = Main.WIN/750;
			this.damage = 5;
		}
		
	// Creating RED Zombies
		if (type==RED) {					
			this.maxHP= 50;
			this.HP = 50;
			this.speed = Main.WIN/525;
			this.damage = 10;
		}
		
	// Creating GOLD Zombies		
		if (type==GOLD) {					
			this.maxHP= 100;
			this.HP = 100;
			this.speed = Main.WIN/300;
			this.damage = 20;
		}
		
		this.x = (int)(Math.random()*Main.WIN);
		this.y = (int)(Math.random()*Main.WIN);
		
		this.type=type;
		
		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
		
	}
	
	void paint(Graphics g) {
		
		if (this.type==GREEN) g.setColor(Color.GREEN);
		if (this.type== BLUE) g.setColor(Color.BLUE);
		if (this.type==  RED) g.setColor(Color.RED);
		if (this.type== GOLD) g.setColor(Color.YELLOW);
		
		
		
		g.fillOval(x, y, Main.WIN/30, Main.WIN/30);
	}
	
	

}
