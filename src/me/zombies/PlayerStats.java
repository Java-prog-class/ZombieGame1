package me.zombies;

class PlayerStats {
	
//Variables
	int x = PlayerMove.WIN/2;
	int y = PlayerMove.WIN/2;
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP;
	double PercentRatio;
	double PercentHP;
	int speed;
	
	PlayerStats(String name){
		this.alive = true;
		this.name = name;
		this.maxHP = 20;
		this.HP = 20;
		this.PercentRatio = ((this.HP*100)/this.maxHP);
		this.PercentHP = PercentRatio/100;
		this.speed = 5;
		this.armor = "None";
		this.weapon = "Fist";
	}
}
