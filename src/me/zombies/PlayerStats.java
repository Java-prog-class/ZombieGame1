package me.zombies;

class PlayerStats 
{
	
	//Variables
	int x = Main.WIN/2; //By Default the Player Positions are in the Center of the Window.
	int y = Main.WIN/2;
	
	int width = Main.WIN/20;
	int height = Main.WIN/20;
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP, speed;
	double PercentRatio, PercentHP, angle;
	
	PlayerStats(String name)
	 {
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
