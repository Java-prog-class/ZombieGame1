package me.zombies;

import java.awt.Rectangle;

@SuppressWarnings("serial")
class PlayerStats extends Rectangle {
	
//Variables
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP, speed;
	double PercentRatio, PercentHP, angle;
	
	PlayerStats(String name) {
		
		x = Main.WIN/2;
		y = Main.WIN/2;
		
		width = Main.WIN/20;
		height = Main.WIN/20;
		
		this.alive = true;
		this.name = name;
		this.maxHP = 20;
		this.HP = 20;
		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
		this.angle = 0.0;
		this.speed = Main.WIN/150;
		this.armor = "None";
		this.weapon = "Fist";
	}
}
