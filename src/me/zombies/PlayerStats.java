package me.zombies;

<<<<<<< HEAD
class PlayerStats 
{
	
	//Variables
	int x = Main.WIN/2; //By Default the Player Positions are in the Center of the Window.
	int y = Main.WIN/2;
	
	int width = Main.WIN/20;
	int height = Main.WIN/20;
=======
import java.awt.Rectangle;

@SuppressWarnings("serial")
class PlayerStats extends Rectangle {
	
//Variables
>>>>>>> master
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP, speed;
	double PercentRatio, PercentHP, angle;
	
<<<<<<< HEAD
	PlayerStats(String name)
	 {
=======
	PlayerStats(String name) {
		
		x = Main.WIN/2;
		y = Main.WIN/2;
		
		width = Main.WIN/20;
		height = Main.WIN/20;
//		height = Main.WIN/15;
		
>>>>>>> master
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
