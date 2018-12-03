package me.zombies;

import java.awt.Color;
import java.awt.Graphics;

class Zombies {

	int Green = 1;
	int Blue = 2;
	int Red = 3;
	int Gold = 4;
	
//Variables
	int x, y;
	
	int width, height;
	
	boolean alive;
	
	int HP, maxHP, speed, damage;
	double PercentRatio, PercentHP, angle;
	
	Zombies(int type){
		
	//TODO: Create Zombie Behaviour Here
		
		

	// Creating GREEN Zombies
		if (type==Green) {					
			this.maxHP= 10;
			this.HP = 10;
			this.speed = Main.WIN/1500;
			this.damage = 1;
		}
		
	// Creating BLUE Zombies	
		if (type==Blue) {					
			this.maxHP= 20;
			this.HP = 20;
			this.speed = Main.WIN/750;
			this.damage = 5;
		}
		
	// Creating RED Zombies
		if (type==Red) {					
			this.maxHP= 50;
			this.HP = 50;
			this.speed = Main.WIN/525;
			this.damage = 10;
		}
		
	// Creating GOLD Zombies		
		if (type==Gold) {					
			this.maxHP= 100;
			this.HP = 100;
			this.speed = Main.WIN/300;
			this.damage = 20;
		}
		
		this.x = (int)(Math.random()*Main.WIN);
		this.y = (int)(Math.random()*Main.WIN);
		
		this.alive = true;
		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
		
	}
	
	void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, 50, 50);
	}
	
	

}
